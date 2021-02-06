package com.kgh.dpiprobe.service;

import com.kgh.dpiprobe.models.Order;
import com.kgh.dpiprobe.models.OrderBook;
import com.kgh.dpiprobe.models.OrderItem;
import com.kgh.dpiprobe.models.Product;

import java.util.List;
import java.util.Optional;
/*
I am combining the oderservice and the productservice in one single service layer,
however, I understand that this should have another level of abstraction given t
he different business domain requirements for products and orders,
but for templating, this is acceptable.
 */
public interface OrderService {
    public abstract boolean createProduct(Product product);
    public abstract Optional<Product> updateProduct(String skuid, Product product);
    public abstract boolean deleteProduct(String id);
    public abstract Optional<Product> retrieveProduct(String skuid);
    public abstract List<Product> getProducts(String title);
/*
*/
    public abstract Optional<OrderBook> retrieveOrderBook(String clientEmail);
    public abstract boolean createOrderBook(OrderBook orderBook);
    public abstract Optional<Order> createOrder(OrderBook orderBook);
    public abstract Optional<Order> retrieveOrder(OrderBook orderBook, String orderId);
    public abstract Optional<Order> updateOrder(OrderBook orderBook, Order order, OrderItem orderItem);

}
