package com.iiitb.customebook.pojo;

import java.util.List;

public class CartVO {

    private Integer orderId;
    private Integer userId;
    private String customEBookName;
    private Integer totalPrice;
    private List<ItemVO> orderItems;

    public CartVO() {
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

    public List<ItemVO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ItemVO> orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
