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

      public Integer getChapterId(CartItemInputVO itemDetails) {

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


    public OrderOutputVO processOrder(Integer userId, String customEBookName)
    {
        Order order = orderRepository.findCartOrderForUser(userId);
        CartVO cartDetails = getUserCartDetails(userId);
        order.setCustomEBookName(customEBookName);
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
                ItemVO item = getBookChapterDetails(chapterItem.getBookId(), chapterItem.getChapterNumber());
                if(item!=null) {
                    cartItems.add(item);
                    totalPrice += item.getPrice();
                }

            }
            cartVO.setOrderItems(cartItems);
            cartVO.setTotalPrice(totalPrice);
            return cartVO;
        }
    }

    private ItemVO getBookChapterDetails(Integer bookId, Integer chapterNumber) {

        Book book = bookService.getBookById(bookId);
        if(book!=null) {
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
        }
        return null;
    }

    public void addItemToCart(Integer userId, CartItemInputVO itemDetails) {
        Integer cartOrderId = orderRepository.findInCartOrderIdForUser(userId);
        if(null == cartOrderId || cartOrderId==0) {
            createNewOrder(userId, itemDetails);
        } else {
            updateOrder(cartOrderId, userId, itemDetails);
        }
    }



    public Order createNewOrder(Integer userId, CartItemInputVO itemDetails) {
        Order order = new Order();
        User user = userService.getUserById(userId);
        order.setUser_id(user);

        order.setChapterItems(String.valueOf(getChapterId(itemDetails)));
        order.setTotalPrice(getChapterPrice(itemDetails.getBookId(), itemDetails.getChapterNumber()));

        order.setOrderStatus(ORDER_STATUS_IN_CART);
        return orderRepository.save(order);
    }

    private void updateOrder(Integer cartOrderId, Integer userId, CartItemInputVO itemDetails) {
        Order order = orderRepository.findById(cartOrderId).orElseThrow(()
                -> new ResourceNotFoundException("Order does not exists with id:"+cartOrderId));

        String chapters[] = order.getChapterItems().split(",");
        int initialLength = chapters.length;
        HashSet<String> uniqueChapters = new HashSet<String>(Arrays.asList(chapters));
        uniqueChapters.add(String.valueOf(getChapterId(itemDetails)));

        StringBuilder chapterItems = new StringBuilder();
        for(String chapter: uniqueChapters) {
            chapterItems.append(chapter+",");
        }
        chapterItems.deleteCharAt(chapterItems.lastIndexOf(","));
        order.setChapterItems(chapterItems.toString());
        int newLength = order.getChapterItems().split(",").length;
        if(initialLength<newLength) {

            order.setTotalPrice(order.getTotalPrice()+getChapterPrice(itemDetails.getBookId(), itemDetails.getChapterNumber()));
        }
        orderRepository.save(order);
    }

    public void deleteItemInCart(Integer userId, CartItemInputVO itemDetails) {

        ChapterItem chapterItem = chapterItemService.getChapterItemByBookIdAndChapterNumber(itemDetails.getBookId(),
                itemDetails.getChapterNumber());
        if(chapterItem==null) {
            return;
        }
        String chapterItemNumber = String.valueOf(chapterItem.getChapterId());

        Order order = orderRepository.findCartOrderForUser(userId);
        String orderItems = order.getChapterItems();
        if(orderItems.contains(chapterItemNumber)) {
            int index = orderItems.indexOf(chapterItemNumber);
            if(index==0){
                order.setChapterItems(orderItems.replace(chapterItemNumber+",",""));
            } else {
                order.setChapterItems(orderItems.replace(","+chapterItemNumber,""));
            }
            double chapterPrice = getChapterPrice(itemDetails.getBookId(), itemDetails.getChapterNumber());
            double totalPrice = order.getTotalPrice() - chapterPrice;
            order.setTotalPrice(totalPrice);

        }
        orderRepository.save(order);
    }

    public double getChapterPrice(Integer bookId, Integer chapterNumber) {
        ItemVO item = getBookChapterDetails(bookId, chapterNumber);
        if(item!=null) {
            return item.getPrice();
        } else
            return 0.0;
    }
}
