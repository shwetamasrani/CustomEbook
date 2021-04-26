package com.iiitb.customebook.pojo;

public class CartInputVO {

    private Integer userId;
    private ItemVO itemDetails;

    public CartInputVO() {
    }

    public CartInputVO(Integer userId, ItemVO itemDetails) {
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
