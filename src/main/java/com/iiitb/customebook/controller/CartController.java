package com.iiitb.customebook.controller;


import com.iiitb.customebook.bean.Order;
import com.iiitb.customebook.pojo.CartItemInputVO;
import com.iiitb.customebook.pojo.CartOutputVO;
import com.iiitb.customebook.pojo.CartVO;
import com.iiitb.customebook.pojo.OrderOutputVO;
import com.iiitb.customebook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private OrderService orderService;

    @Autowired
    public CartController(OrderService orderService) {
        this.orderService = orderService;
    }

    /*@PostMapping("/checkout")
    public ResponseEntity<OrderOutputVO> merge(@RequestBody CartVO orderDetails)  //mapping the JSON Body to the object directly
    {
        if(orderDetails!=null) {
            OrderOutputVO orderMerged = orderService.processOrder(orderDetails);
            return new ResponseEntity(orderMerged, HttpStatus.CREATED);
        }
        return null;
    }*/

   /* @PostMapping("/add")
    public ResponseEntity<CartOutputVO> addToCart(@RequestBody CartItemInputVO itemDetails)  //mapping the JSON Body to the object directly
    {
        if(itemDetails!=null) {
            Order order = orderService.addItem(itemDetails);
            CartOutputVO updatedCartDetails = mappingOrderToCartOutputVO(order);
            updatedCartDetails.setUserId(itemDetails.getUserId());
            return new ResponseEntity(updatedCartDetails, HttpStatus.CREATED);
        }
        return null;
    }*/

    public CartOutputVO mappingOrderToCartOutputVO(Order order) {
        CartOutputVO updatedCartDetails = new CartOutputVO();
        updatedCartDetails.setOrderId(order.getOrderId());
        updatedCartDetails.setTotalNumberOfItems(order.getChapterItems().split(",").length);
        return updatedCartDetails;

    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<CartVO> getOrderDetails(@PathVariable Integer orderId) {
        CartVO cartDetails = new CartVO();
        cartDetails.setOrderId(orderId);
        //orderService.getCartDetails(cartDetails, orderId);
        return ResponseEntity.ok(cartDetails);
    }
}
