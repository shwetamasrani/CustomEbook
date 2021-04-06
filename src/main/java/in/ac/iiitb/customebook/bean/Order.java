package in.ac.iiitb.customebook.bean;

import javax.persistence.*;

@Entity
public class Order implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(nullable = false)
    private Boolean Status;

    @Column(nullable = true)
    private double price;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public  Order(){

    }

    public Order(Integer orderId, Boolean status, double price) { //gotta add order id
        this.orderId = orderId;
        Status = status;
        this.price = price;
    }
}
