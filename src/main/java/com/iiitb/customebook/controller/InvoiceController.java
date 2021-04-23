package com.iiitb.customebook.controller;


import com.iiitb.customebook.pojo.OrderInputVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class InvoiceController {

    @PostMapping
    public void addBook(@RequestBody OrderInputVO orderDetails)  //mapping the JSON Body to the object directly
    {
        if(orderDetails!=null) {

        }
    }
}
