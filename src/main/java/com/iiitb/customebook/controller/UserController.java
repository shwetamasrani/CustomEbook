package com.iiitb.customebook.controller;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.bean.Order;
import com.iiitb.customebook.bean.User;
import com.iiitb.customebook.pojo.*;
import com.iiitb.customebook.service.BookService;
import com.iiitb.customebook.service.OrderService;
import com.iiitb.customebook.service.UserService;
import com.iiitb.customebook.util.CustomEBookUtil;
import org.apache.coyote.Response;
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
    public ResponseEntity<Object> createUser(@RequestBody User newUser)  //mapping the JSON Body tot he object directly
    {
        User user = userService.createUser(newUser);
        if(user!=null) {
            if(user.isPublisherFlag()) {
                return ResponseEntity.ok(getPublisherDetails(user));
            }
            return ResponseEntity.ok(getCustomerDetails(user));
        }
        return null;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable Integer id) {

        User user = userService.getUserById(id);
        if(user!=null) {
            if(user.isPublisherFlag()) {
                return ResponseEntity.ok(getPublisherDetails(user));
            }
            return ResponseEntity.ok(getCustomerDetails(user));
        }
        return null;

    }

    @GetMapping("users/{id}/orders")
    public ResponseEntity<CustomerDetails> getUserOrders(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if(user!=null) {
            return ResponseEntity.ok(getCustomerDetails(user));
        }
        return null;

    }

    @GetMapping("users/{id}/uploadedBooks")
    public ResponseEntity<PublisherDetails> getPublisherUploads(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if(user!=null) {
            return ResponseEntity.ok(getPublisherDetails(user));
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
            return ResponseEntity.ok(getUserDetails(userDetails));
        }
        else {
            return null;
        }
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

    @GetMapping("/users/{userId}/cart")
    public ResponseEntity<CartVO> getUserCartDetails(@PathVariable Integer userId) {
        if(userId!=null) {
            CartVO cartDetails = orderService.getUserCartDetails(userId);
            if(cartDetails.getOrderItems().size()>0){
                return ResponseEntity.ok(cartDetails);
            } else {
                return new ResponseEntity(cartDetails, HttpStatus.CREATED);
            }

        }
        return null;
    }

    @PostMapping("/users/{userId}/cart")
    public HttpStatus addToCart(@PathVariable Integer userId, @RequestBody CartItemInputVO itemDetails) {
        if(userId!=null) {
            orderService.addItemToCart(userId, itemDetails);
            return HttpStatus.CREATED;

        }
        return null;
    }

    @DeleteMapping("/users/{userId}/cart")
    public HttpStatus deleteItemInCart(@PathVariable Integer userId, @RequestBody CartItemInputVO itemDetails) {
        if(userId!=null) {
            orderService.deleteItemInCart(userId, itemDetails);
            return HttpStatus.NO_CONTENT;
        }
        return null;
    }

    @PostMapping("/users/{userId}/cart/checkout/{bookName}")
    public HttpStatus checkOut(@PathVariable Integer userId, @PathVariable String bookName)
    {
        if(userId!=null) {
            orderService.processOrder(userId, bookName);
            return HttpStatus.CREATED;
        }

        return null;
    }

    private UserDetails getUserDetails(User user) {

        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(user.getUser_id());
        userDetails.setPublisherFlag(user.isPublisherFlag());
        userDetails.setEmailAddress(user.getEmail());
        return userDetails;
    }

    private CustomerDetails getCustomerDetails(User user) {
        CustomerDetails customerDetails = new CustomerDetails(getUserDetails(user));

        customerDetails.setFirstName(user.getFirstName());
        customerDetails.setLastName(user.getLastName());
        List<OrderDetails> userOrders = new ArrayList<>();
        if(null != user.getOrders()) {
            for(Order order: user.getOrders()) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderId(order.getOrderId());
                orderService.getOrderDetails(orderDetails);
                userOrders.add(orderDetails);
            }
        }
        customerDetails.setOrders(userOrders);
        return customerDetails;
    }

    private PublisherDetails getPublisherDetails(User user) {
        PublisherDetails publisherDetails = new PublisherDetails(getUserDetails(user));
        publisherDetails.setCompanyName(user.getCompanyName());
        List<BookVO> uploadedBooks = new ArrayList<>();
        if(null!=user.getUploaded_books()) {
            for(Book book: user.getUploaded_books()) {
                BookVO bookDetails = CustomEBookUtil.mappingBookBeanToPojo(book);
                uploadedBooks.add(bookDetails);
            }
        }

        publisherDetails.setUploadedBooks(uploadedBooks);
        return publisherDetails;
    }
}
