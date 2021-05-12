package com.iiitb.customebook.pojo;

public class CartItemInputVO {

    private Integer userId;
    private ItemVO itemDetails;

    public CartItemInputVO() {
    }

    public CartItemInputVO(Integer userId, ItemVO itemDetails) {
        this.userId = userId;
        this.itemDetails = itemDetails;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ItemVO getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemVO itemDetails) {
        this.itemDetails = itemDetails;
    }
}
