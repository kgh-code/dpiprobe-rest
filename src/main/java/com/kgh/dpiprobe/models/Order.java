package com.kgh.dpiprobe.models;

import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

/**
 * Try and keep this pojo as implementation agnostic as possible - createdDate is mongo specific - candidate for abstraction.
 */
@ApiModel(value="Order", description="A list of order items, with the date the order was created and a total of the order value")
public class Order {

    @CreatedDate
    private Date createdDate;

    private String orderId;

    private Double orderTotal;

    private List<OrderItem> orderItems;

    public Order() {}

    public Date getcreatedDate() {
        return createdDate;
    }

    public void setcreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order createdData=" + createdDate
                + ", orderId=" + orderId
                + ", orderTotal=" + orderTotal
                + "]";
    }
}