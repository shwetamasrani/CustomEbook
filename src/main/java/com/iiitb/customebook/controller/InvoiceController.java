package com.iiitb.customebook.controller;


import com.iiitb.customebook.pojo.OrderInputVO;
import com.iiitb.customebook.pojo.OrderOutputVO;
import com.iiitb.customebook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class InvoiceController {

    private OrderService orderService;

    @Autowired
    public InvoiceController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderOutputVO> createOrder(@RequestBody OrderInputVO orderDetails)  //mapping the JSON Body to the object directly
    {
        if(orderDetails!=null) {
            OrderOutputVO orderMerged = orderService.createOrder(orderDetails);
            return new ResponseEntity(orderMerged, HttpStatus.CREATED);
        }
        return null;
    }
}
