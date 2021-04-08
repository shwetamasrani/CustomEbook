package com.iiitb.customebook.bean;

import javax.persistence.*;

@Entity
public class Order implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(nullable = false)
    private Boolean orderStatus;

    @Column(nullable = false)
    private String customEBookName;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = true)
    private double totalPrice;

    public Order() {
    }

    public Order(Boolean orderStatus, String customEBookName, User user, double totalPrice) {
        this.orderStatus = orderStatus;
        this.customEBookName = customEBookName;
        this.user = user;
        this.totalPrice = totalPrice;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomEBookName() {
        return customEBookName;
    }

    public void setCustomEBookName(String customEBookName) {
        this.customEBookName = customEBookName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
