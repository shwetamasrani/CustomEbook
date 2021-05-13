package com.iiitb.customebook.controller;

import com.iiitb.customebook.pojo.OrderDetails;
import com.iiitb.customebook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetails> getOrderDetails(@PathVariable Integer orderId) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderId);
        orderService.getOrderDetails(orderDetails);
        return ResponseEntity.ok(orderDetails);
    }
}
