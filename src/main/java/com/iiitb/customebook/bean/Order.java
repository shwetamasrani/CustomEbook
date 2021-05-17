package com.iiitb.customebook.bean;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders_TBL")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(nullable = false) // C-In Cart and P-Processed & Merged
    private Character orderStatus;

    @Column
    private String customEBookName;

    @Column(nullable = false)
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    private String chapterItems;

    @Column
    private String location;

    @Column
    private LocalDateTime orderDate;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Character getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Character orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomEBookName() {
        return customEBookName;
    }

    public void setCustomEBookName(String customEBookName) {
        this.customEBookName = customEBookName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public String getChapterItems() {
        return chapterItems;
    }

    public void setChapterItems(String chapterItems) {
        this.chapterItems = chapterItems;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Order() {
    }

}
