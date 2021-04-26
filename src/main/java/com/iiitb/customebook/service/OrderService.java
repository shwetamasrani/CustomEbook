package com.iiitb.customebook.service;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.bean.ChapterItem;

import com.iiitb.customebook.bean.Order;
import com.iiitb.customebook.bean.User;
import com.iiitb.customebook.exception.ResourceNotFoundException;
import com.iiitb.customebook.pojo.*;

import com.iiitb.customebook.repository.OrderRepository;
import com.iiitb.customebook.util.CustomEBookConstants;
import com.iiitb.customebook.util.CustomEBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


@Service
public class OrderService {

    private final OrderRepository orderRepository;


    @Autowired
    UserService userService;
    @Autowired
    ChapterItemService chapterItemService;
    @Autowired
    BookService bookService;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order addItem(CartItemInputVO cartInputDetails){
        Integer cartOrderId = orderRepository.findInCartOrderIdForUser(cartInputDetails.getUserId());
        if(null == cartOrderId || cartOrderId==0) {
            return orderRepository.save(createNewOrder(cartInputDetails));
        } else {
            Order order = orderRepository.findById(cartOrderId).orElseThrow(()
                    -> new ResourceNotFoundException("Order does not exists with id:"+cartOrderId));
            return updateOrder(cartInputDetails, order);

        }
    }

    public Integer getChapterId(ItemVO itemDetails) {

        if(itemDetails!=null) {
            ChapterItem chapterItem = chapterItemService.getChapterItemByBookIdAndChapterNumber(itemDetails.getBookId(),
                    itemDetails.getChapterNumber());
            if (chapterItem == null) {
                chapterItem = new ChapterItem();
                chapterItem.setBookId(itemDetails.getBookId());
                chapterItem.setChapterNumber(itemDetails.getChapterNumber());
                chapterItemService.addChapterItem(chapterItem);

            }
            return chapterItem.getChapterId();
        }
        return null;
    }

    public Order createNewOrder(CartItemInputVO cartInputDetails) {
        Order order = new Order();
        User user = userService.getUserById(cartInputDetails.getUserId());
        order.setUser_id(user);
        order.setChapterItems(String.valueOf(getChapterId(cartInputDetails.getItemDetails())));
        order.setTotalPrice(cartInputDetails.getItemDetails().getPrice());
        order.setOrderStatus(CustomEBookConstants.ORDER_STATUS_IN_CART);

        return order;
    }

    public Order updateOrder(CartItemInputVO cartInputDetails, Order order) {
        String chapters[] = order.getChapterItems().split(",");
        int initialLength = chapters.length;

        HashSet<String> uniqueChapters = new HashSet<String>(Arrays.asList(chapters));
        uniqueChapters.add(getChapterId(cartInputDetails.getItemDetails())+"");
        StringBuilder chapterItems = new StringBuilder();
        for(String chapter: uniqueChapters) {
            chapterItems.append(chapter+",");
        }
        chapterItems.deleteCharAt(chapterItems.lastIndexOf(","));
        order.setChapterItems(chapterItems.toString());
        int newLength = order.getChapterItems().split(",").length;
        if(initialLength<newLength) {
            order.setTotalPrice(order.getTotalPrice()+cartInputDetails.getItemDetails().getPrice());
        }
        return orderRepository.save(order);
    }


    public OrderOutputVO processOrder(CartVO orderDetails)
    {
        Order order = orderRepository.findById(orderDetails.getOrderId()).orElseThrow(()
                -> new ResourceNotFoundException("Order does not exists with id:"+orderDetails.getOrderId()));
        order.setCustomEBookName(orderDetails.getCustomEBookName());
        String mergedFileLocation = PDFMerge.merge(order.getOrderId(), orderDetails.getOrderItems());
        order.setLocation(mergedFileLocation);
        order.setOrderStatus(CustomEBookConstants.ORDER_STATUS_PROCESSED);
        orderRepository.save(order);
        return new OrderOutputVO(order.getOrderId(), mergedFileLocation, order.getTotalPrice());
    }


    public void getCartDetails(CartVO cartDetails, Integer orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(()
                -> new ResourceNotFoundException("Order does not exists with id:"+orderId));
        User user = userService.getUserById(order.getUser_id().getUser_id());
        cartDetails.setUserId(user.getUser_id());
        String chapters[] = order.getChapterItems().split(",");

        for(String chapter : chapters) {
            int chapterId = Integer.valueOf(chapter);
            ChapterItem chapterItem = chapterItemService.getChapterItemById(chapterId);
            Book book = bookService.getBookById(chapterItem.getBookId());
            if(book!=null) {
                BookComponent chapterDetails = getChapterDetails(book, chapterItem.getChapterNumber());
                if(chapterDetails!=null) {
                    ItemVO cartItem = new ItemVO();
                    cartItem.setBookId(book.getBookId());
                    cartItem.setPrice(chapterDetails.getPrice());
                    cartItem.setChapterNumber(chapterDetails.getChapterNumber());
                    cartItem.setStartPage(chapterDetails.getStartPage());
                    cartItem.setEndPage(chapterDetails.getEndPage());
                    cartItem.setBookLocation(book.getPdfFileLocation());
                    if(cartDetails.getOrderItems()!=null) {
                        cartDetails.getOrderItems().add(cartItem);
                    }
                   else {
                       cartDetails.setOrderItems(Arrays.asList(cartItem));
                    }
                }
            }

        }

    }

    public BookComponent getChapterDetails(Book book, int chapterNumber) {

        return CustomEBookUtil.getChapterDetails(book, chapterNumber);
    }
}
