package com.iiitb.customebook.service;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.bean.Invoice;
import com.iiitb.customebook.bean.InvoiceItem;
import com.iiitb.customebook.bean.User;
import com.iiitb.customebook.exception.ResourceNotFoundException;
import com.iiitb.customebook.pojo.BookVO;
import com.iiitb.customebook.pojo.OrderInputVO;
import com.iiitb.customebook.pojo.OrderItemInputVO;
import com.iiitb.customebook.pojo.OrderOutputVO;
import com.iiitb.customebook.repository.OrderRepository;
import com.iiitb.customebook.repository.UserRepository;
import com.iiitb.customebook.util.CustomEBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderOutputVO createOrder(OrderInputVO orderDetails)
    {
        Invoice invoice =  orderRepository.save(mappingPojoToBean(orderDetails));
        String mergedFileLocation = PDFMerge.merge(invoice.getInvoice_id(), orderDetails.getOrderItems());
        return new OrderOutputVO(invoice.getInvoice_id(), mergedFileLocation, invoice.getTotalPrice());
    }

    private Invoice mappingPojoToBean(OrderInputVO orderDetails) {

        Invoice invoice = new Invoice();
        invoice.setCustomEBookName(orderDetails.getCustomEBookName());
        User user = userService.getUserById(orderDetails.getUserId());
        invoice.setUser_id(user);
        setOrderItems(invoice, orderDetails.getOrderItems());
        return invoice;
    }

    private void setOrderItems(Invoice invoice, List<OrderItemInputVO> itemsDetails) {
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        double totalPrice = 0.0;
        InvoiceItem invoiceItem = new InvoiceItem();
        for(OrderItemInputVO orderItem: itemsDetails) {
            invoiceItem.setBook_id(orderItem.getBookId());
            invoiceItem.setChapter_number(orderItem.getChapterNumber());
            invoiceItem.setChapter_price(orderItem.getPrice());
            invoiceItems.add(invoiceItem);
            totalPrice += orderItem.getPrice();
        }
        invoice.setItems(invoiceItems);
        invoice.setTotalPrice(totalPrice);
    }
}
