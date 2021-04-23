package com.iiitb.customebook.pojo;

import java.util.List;

public class OrderInputVO {

    private String userId;
    private String customEBookName;
    private List<OrderItemInputVO> orderItems;

    public OrderInputVO() {
    }

    public OrderInputVO(String userId, String customEBookName, List<OrderItemInputVO> orderItems) {
        this.userId = userId;
        this.customEBookName = customEBookName;
        this.orderItems = orderItems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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
