package com.iiitb.customebook.pojo;

import java.util.List;

public class OrderInputVO {

    private Integer orderId;
    private Integer userId;
    private String customEBookName;
    private List<OrderItemInputVO> orderItems;

    public OrderInputVO() {
    }

    public OrderInputVO(Integer orderId, Integer userId, String customEBookName, List<OrderItemInputVO> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.customEBookName = customEBookName;
        this.orderItems = orderItems;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCustomEBookName() {
        return customEBookName;
    }

    public void setCustomEBookName(String customEBookName) {
        this.customEBookName = customEBookName;
    }

    public List<OrderItemInputVO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemInputVO> orderItems) {
        this.orderItems = orderItems;
    }
}
