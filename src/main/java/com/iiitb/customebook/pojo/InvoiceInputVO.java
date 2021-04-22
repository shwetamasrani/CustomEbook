package com.iiitb.customebook.pojo;

import java.util.List;

public class InvoiceInputVO {

    private String userId;
    private String customEBookName;
    private List<InvoiceItemInputVO> invoiceItems;

    public InvoiceInputVO() {
    }

    public InvoiceInputVO(String userId, String customEBookName, List<InvoiceItemInputVO> invoiceItems) {
        this.userId = userId;
        this.customEBookName = customEBookName;
        this.invoiceItems = invoiceItems;
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

    public List<InvoiceItemInputVO> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItemInputVO> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}
