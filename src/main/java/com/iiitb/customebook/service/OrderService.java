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
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.iiitb.customebook.util.CustomEBookConstants.*;


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

    /*public Order addItem(CartItemInputVO cartInputDetails){
        Integer cartOrderId = orderRepository.findInCartOrderIdForUser(cartInputDetails.getUserId());
        if(null == cartOrderId || cartOrderId==0) {
            return orderRepository.save(createNewOrder(cartInputDetails));
        } else {
            Order order = orderRepository.findById(cartOrderId).orElseThrow(()
                    -> new ResourceNotFoundException("Order does not exists with id:"+cartOrderId));
            return updateOrder(cartInputDetails, order);

        }
    }*/

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

   /* public Order createNewOrder(CartItemInputVO cartInputDetails) {
        Order order = new Order();
        User user = userService.getUserById(cartInputDetails.getUserId());
        order.setUser_id(user);
        order.setChapterItems(String.valueOf(getChapterId(cartInputDetails.getItemDetails())));
        order.setTotalPrice(cartInputDetails.getItemDetails().getPrice());
        order.setOrderStatus(ORDER_STATUS_IN_CART);

        return order;
    }*/

   /* public Order updateOrder(CartItemInputVO cartInputDetails, Order order) {
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
    }*/


    public OrderOutputVO processOrder(CartVO cartDetails)
    {
        Order order = orderRepository.findById(cartDetails.getOrderId()).orElseThrow(()
                -> new ResourceNotFoundException("Order does not exists with id:"+cartDetails.getOrderId()));
        order.setCustomEBookName(cartDetails.getCustomEBookName());
        String mergedFileLocation = PDFMerge.merge(order.getOrderId(), cartDetails.getOrderItems());
        order.setLocation(mergedFileLocation);
        order.setOrderStatus(CustomEBookConstants.ORDER_STATUS_PROCESSED);
        orderRepository.save(order);
        return new OrderOutputVO(order.getOrderId(), mergedFileLocation, order.getTotalPrice());
    }


    public BookComponent getChapterDetails(Book book, int chapterNumber) {

        return CustomEBookUtil.getChapterDetails(book, chapterNumber);
    }

    public void getOrderDetails(OrderDetails orderDetails) {
        Order order = orderRepository.findById(orderDetails.getOrderId()).orElseThrow(()
                -> new ResourceNotFoundException("Order does not exists with id:"+orderDetails.getOrderId()));
        if(order.getOrderStatus()==ORDER_STATUS_IN_CART) {
            orderDetails.setOrderStatus(ORDER_IN_CART);
        } else if(order.getOrderStatus()==ORDER_STATUS_PROCESSED) {
            orderDetails.setOrderStatus(ORDER_PROCESSED);
        }
        orderDetails.setCustomEBookName(order.getCustomEBookName());
        orderDetails.setTotalPrice(order.getTotalPrice());

    }

    public CartVO getUserCartDetails(Integer userId) {
        Integer cartOrderId = orderRepository.findInCartOrderIdForUser(userId);
        if(null == cartOrderId || cartOrderId==0) {
            return null;
        } else {
            Order order = orderRepository.findById(cartOrderId).orElseThrow(()
                    -> new ResourceNotFoundException("Order does not exists with id:"+cartOrderId));
            CartVO cartVO = new CartVO();
            cartVO.setOrderId(order.getOrderId());
            cartVO.setUserId(userId);
            if(order.getCustomEBookName()!=null && !order.getCustomEBookName().equals(EMPTY_STRING)) {
                cartVO.setCustomEBookName(order.getCustomEBookName());
            }
            List<ItemVO> cartItems = new ArrayList<>();
            int totalPrice = 0;
            String chapters[] = order.getChapterItems().split(",");
            for(String chapter : chapters) {
                int chapterId = Integer.valueOf(chapter);
                ChapterItem chapterItem = chapterItemService.getChapterItemById(chapterId);
                Book book = bookService.getBookById(chapterItem.getBookId());
                if(book!=null) {
                    ItemVO item = getBookChapterDetails(book, chapterItem.getChapterNumber());
                    if(item!=null) {
                        cartItems.add(item);
                        totalPrice += item.getPrice();
                    }
                }
            }
            cartVO.setOrderItems(cartItems);
            cartVO.setTotalPrice(totalPrice);
            return cartVO;
        }
    }

    private ItemVO getBookChapterDetails(Book book, Integer chapterNumber) {

        BookComponent chapterDetails = getChapterDetails(book, chapterNumber);
        if(chapterDetails!=null) {
            ItemVO cartItem = new ItemVO();
            cartItem.setBookId(book.getBookId());
            cartItem.setBookName(book.getBookName());
            cartItem.setBookLocation(book.getPdfFileLocation());
            cartItem.setChapterName(chapterDetails.getChapterName());
            cartItem.setChapterNumber(chapterDetails.getChapterNumber());
            cartItem.setStartPage(chapterDetails.getStartPage());
            cartItem.setEndPage(chapterDetails.getEndPage());
            cartItem.setPrice(chapterDetails.getPrice());
            cartItem.setChapterDescription(chapterDetails.getDescription());
            return cartItem;
        }
        return null;
    }

}
