package com.iiitb.customebook.controller;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.bean.Order;
import com.iiitb.customebook.bean.User;
import com.iiitb.customebook.pojo.*;
import com.iiitb.customebook.service.BookService;
import com.iiitb.customebook.service.OrderService;
import com.iiitb.customebook.service.UserService;
import com.iiitb.customebook.util.CustomEBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/")
public class UserController {


    private UserService userService;
    private OrderService orderService;
    private BookService bookService;

    @Autowired
    public UserController(UserService userService, OrderService orderService, BookService bookService) {
        this.userService = userService;
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }


    @PostMapping("/users")
    public ResponseEntity<UserDetails> createUser(@RequestBody User newUser)  //mapping the JSON Body tot he object directly
    {
        User user = userService.createUser(newUser);
        if(user!=null) {
            return ResponseEntity.ok(mappingUserBeanToPojo(user));
        }
        return null;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable Integer id) {

        User user = userService.getUserById(id);
        if(user!=null) {
            return ResponseEntity.ok(mappingUserBeanToPojo(user));
        }
        return null;

    }

    @GetMapping("users/{id}/orders")
    public ResponseEntity<UserDetails> getUserOrders(@PathVariable Integer id) {
        System.out.println("Is this coming here??");
        User user = userService.getUserById(id);
        if(user!=null) {

            return ResponseEntity.ok(mappingUserBeanToPojo(user));
        }
        return null;

    }

    @PutMapping("users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDetails> login(@RequestBody LoginDetailsVO loginDetails){

        User userDetails = userService.getUserByEmail(loginDetails.getEmailAddress());

        if(userDetails.getEmail().equals(loginDetails.getEmailAddress())
                && userDetails.getPassword().equals(loginDetails.getPassword())) {
            return ResponseEntity.ok(mappingUserBeanToPojo(userDetails));
        }
        else {
            return null;
        }
    }

    @PostMapping("/users/{userId}/cart/checkout")
    public ResponseEntity<OrderOutputVO> merge(@RequestBody CartVO orderDetails)  //mapping the JSON Body to the object directly
    {
        if(orderDetails!=null) {
            OrderOutputVO orderMerged = orderService.processOrder(orderDetails);
            return new ResponseEntity(orderMerged, HttpStatus.CREATED);
        }
        return null;
    }

    @PostMapping("/users/{userId}/uploadBook")
    public ResponseEntity<Object> addBook(@PathVariable Integer userId, @RequestBody BookVO bookDetails)  //mapping the JSON Body to the object directly
    {
        User user = userService.getUserById(userId);
        if(user!=null && bookDetails!=null) {
            BookVO book = bookService.uploadBook(bookDetails, user);
            if(book!=null) {
                return new ResponseEntity(book, HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest().body("Book already exists");
            }
        }
        return null;
    }

    private UserDetails mappingUserBeanToPojo(User user) {

        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(user.getUser_id());
        userDetails.setPublisherFlag(user.isPublisherFlag());
        userDetails.setEmailAddress(user.getEmail());

        if (user.isPublisherFlag()) {
            userDetails.setCompanyName(user.getCompanyName());
            //publisherDetails.setUploadedBooks();
            return userDetails;
        } else {

            userDetails.setFirstName(user.getFirstName());
            userDetails.setLastName(user.getLastName());
            List<OrderDetails> userOrders = new ArrayList<>();
            for(Order order: user.getOrders()) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderId(order.getOrderId());
                orderService.getOrderDetails(orderDetails);
                userOrders.add(orderDetails);
            }
            userDetails.setOrders(userOrders);
            return userDetails;
        }
    }
}
