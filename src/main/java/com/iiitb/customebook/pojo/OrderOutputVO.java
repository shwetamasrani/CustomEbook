package com.iiitb.customebook.pojo;

public class OrderOutputVO {

    private Integer orderId;
    private String mergedPdfLocation;
    private Double totalPrice;

    public OrderOutputVO() {
    }

    public OrderOutputVO(Integer orderId, String mergedPdfLocation, Double totalPrice) {
        this.orderId = orderId;
        this.mergedPdfLocation = mergedPdfLocation;
        this.totalPrice = totalPrice;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getMergedPdfLocation() {
        return mergedPdfLocation;
    }

    public void setMergedPdfLocation(String mergedPdfLocation) {
        this.mergedPdfLocation = mergedPdfLocation;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
