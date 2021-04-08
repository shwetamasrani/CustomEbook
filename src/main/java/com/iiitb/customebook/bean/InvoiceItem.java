package com.iiitb.customebook.bean;
import javax.persistence.*;

@Entity
@Table
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int invoice_item_id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice_id;

    @Column(nullable = false)
    private Integer book_id;

    @Column(nullable = false)
    private Integer chapter_number;

    @Column(nullable = false)
    private Double chapter_price;

    public int getInvoice_item_id() {
        return invoice_item_id;
    }

    public void setInvoice_item_id(int invoice_item_id) {
        this.invoice_item_id = invoice_item_id;
    }

    public Invoice getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(Invoice invoice_id) {
        this.invoice_id = invoice_id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getChapter_number() {
        return chapter_number;
    }

    public void setChapter_number(Integer chapter_number) {
        this.chapter_number = chapter_number;
    }

    public Double getChapter_price() {
        return chapter_price;
    }

    public void setChapter_price(Double chapter_price) {
        this.chapter_price = chapter_price;
    }

    public InvoiceItem() {
    }

    public InvoiceItem(Invoice invoice_id, Integer book_id, Integer chapter_number, Double chapter_price) {
        this.invoice_id = invoice_id;
        this.book_id = book_id;
        this.chapter_number = chapter_number;
        this.chapter_price = chapter_price;
    }
}
