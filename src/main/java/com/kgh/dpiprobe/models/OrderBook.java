package com.kgh.dpiprobe.models;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Try and keep this pojo as implementation agnostic as possible - createdDate is mongo specific - candidate for abstraction.
 */
@Document(collection = "orderbooks")
@ApiModel(value="OrderBook", description="A basic order book linking clients to lists of orders")
public class OrderBook {

    @Id
    private String id;

    @CreatedDate
    private Date createdDate;

    private String clientEmail;
    private List<Order> orders;


    public OrderBook() {

    }
    public OrderBook(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public OrderBook(String clientEmail, List<Order> orders) {
        this.clientEmail = clientEmail;
        this.orders = orders;
    }


    public String getId() {
        return id;
    }

    public Date getcreatedDate() {
        return createdDate;
    }

    public void setcreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }



    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderBook [id=" + id
                + ", createdData=" + createdDate
                + ", clientEmail=" + clientEmail
                + "]";
    }
}