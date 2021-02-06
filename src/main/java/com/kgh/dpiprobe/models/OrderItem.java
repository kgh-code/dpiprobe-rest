package com.kgh.dpiprobe.models;

import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Should be embeded in the mongo document 'Orders'
 */
@Document(collection = "orderitems")
@ApiModel(value="OrderItem", description="Line items with reference to product Skuids, and quantity")
public class OrderItem {

    private String skuid;
    private Integer quantity;
    private Double lineItemTotal;

    public OrderItem() {}

    public OrderItem(String skuid, Integer quantity) {
        this.skuid = skuid;
        this.quantity = quantity;
    }

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getLineItemTotal() {
        return lineItemTotal;
    }

    public void setLineItemTotal(Double lineItemTotal) {
        this.lineItemTotal = lineItemTotal;
    }

    @Override
    public String toString() {
        return "Product [ skuid=" + skuid
                + ", quantity=" + quantity
                + ", lineItemTotal=" + lineItemTotal
                + "]";
    }
}