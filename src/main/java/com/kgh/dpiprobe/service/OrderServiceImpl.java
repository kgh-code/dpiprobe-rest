package com.kgh.dpiprobe.service;

import com.kgh.dpiprobe.dao.OrderBookDao;
import com.kgh.dpiprobe.dao.ProductDao;
import com.kgh.dpiprobe.models.Order;
import com.kgh.dpiprobe.models.OrderBook;
import com.kgh.dpiprobe.models.OrderItem;
import com.kgh.dpiprobe.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/*

Service layer is implementing mongo auditing (created, last update timestamps) but in production
this should be at the DAO service layer
or configured in boiler plate code.

 */
@Service
//@EnableMongoAuditing
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductDao productDao;

    @Autowired
    OrderBookDao orderBookDao;

    Random ran = new Random();

    /**
     * @Assume Any business validation / rules are applied for creation
     *         Normally a transaction would be used
     *         Normally as this is an asynchronous function (persistence is elsewhere)
     *         Normally I would add a decorator to determine the correct SKU_ID based on business rules
     *
     * @param product
     * @return true = product created, false = could not create
     */
    @Override
    public boolean createProduct(Product product) {
        try {
            product.setPublished(true);
            product.setSkuid(getNewSkuid(product));
            productDao.save(product);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /*
    Only allowing find by Skuid as exposing an physical key (implemented in Mongo) is a security threat
    However, the  Skuid is a logical construct that is determined by the business.
     */
    @Override
    public Optional<Product> retrieveProduct(String skuid) {
        return productDao.findBySkuid(skuid);
    }

    /**
     * Only allowing update of certian business domain fields (title,description,etc..)
     *
     * @param skuid
     * @param _candidate
     * @return optional product
     */
    @Override
    public Optional<Product> updateProduct(String skuid, Product _candidate) {

        Optional<Product> ProductData = productDao.findBySkuid(skuid);

        if (ProductData.isPresent()) {

            try {
                Product _product = ProductData.get();

                _product.setTitle(_candidate.getTitle());
                _product.setDescription(_candidate.getDescription());
                _product.setCompound(_candidate.getCompound());
                _product.setClassification(_candidate.getClassification());
                _product.setDose(_candidate.getDose());
                _product.setPrice(_candidate.getPrice());

                productDao.save(_product);
                System.out.println(_product);
                return productDao.findBySkuid(_product.getSkuid());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("fail");
            return null;
        }
    }

    /**
     * Soft deletion IE, set the published field to false so it does not show in list queries
     * however, you can still retrieve this document as the skuid does not change.
     *
     * @param skuid
     * @return deleted or not
     */
    @Override
    public boolean deleteProduct(String skuid) {
        Optional<Product> ProductData = productDao.findBySkuid(skuid);

        if (ProductData.isPresent()) {

            try {
                Product _product = ProductData.get();
                _product.setPublished(false);
                productDao.save(_product);
                System.out.println(_product);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
     /**
     *  @TODO mongodb _id mask
     * - field should not be sent to the client
     * - this needs to be immplemented using a view mask
     * - best practice to hide physical implementaiton of data persistance.
     */
    @Override
    public List<Product> getProducts(String title) {
        try {
            List<Product> products = new ArrayList<Product>();

            if (title == null) {
                productDao.findByPublished(true).forEach(products::add);
            } else {
                productDao.findByTitleContaining(title).forEach(products::add);
            }
            return products;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
/*----------------------------------------------------------------------------------------------------------------------

OrderBook service calls
 */
   /**
    * Retrieve an order book for a client based on the clientemail
    *
    * @param clientEmail = email of client
    * @return orderBook of client
    */
    @Override
    public Optional<OrderBook> retrieveOrderBook(String clientEmail) {
        return orderBookDao.findByClientEmail(clientEmail);
    }

    /**
     * Create an order book for a client based on their emmail address
     *
     * @param orderBook
     * @return a new orderbook
     */
    @Override
    public boolean createOrderBook(OrderBook orderBook) {
        try {
            orderBookDao.save(orderBook);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Create an order for an existing order book
     *
     * @param orderBook
     * @return a new order in the orderbook.
     */

    @Override
    public Optional<Order> createOrder(OrderBook orderBook) {
        try {

            Order _order = new Order();
            _order.setcreatedDate(new Date());
            _order.setOrderTotal(0D);
            _order.setOrderId(getNewOrderid(orderBook));
            /*
            Don't like this, its a bit hacky
             */
            if (orderBook.getOrders() == null) {
                List<Order> current_orders = new ArrayList<Order>();
                current_orders.add(_order);
                orderBook.setOrders(current_orders);
            } else {
                orderBook.getOrders().add(_order);
            }


            orderBookDao.save(orderBook);
            return Optional.of(_order);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    /**
     * Using a stream to retrieve the order as the orderbook is given.
     *
     * @prarm orderBook = order book containing a list of orders
     * @Param orderId = the order you are retrieving based on the order ID.
     * @return An order to place items against
     */
    @Override
    public Optional<Order> retrieveOrder(OrderBook orderBook, String orderId) {

        try {

            if (orderBook.getOrders() == null) {
                return Optional.empty();
            } else {
                List<Order> current_orders = orderBook.getOrders();

                Order order = current_orders.stream()
                        .filter(_order -> orderId.equals(_order.getOrderId()))
                        .findAny()
                        .orElse(null);
                if (order == null) {
                    return Optional.empty();
                } else {
                    return Optional.of(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    /**
     *   Retrieve the product, based on the skuid, get the price, multiply the quantiy of the line item by this.
     *   Add the line item to the order.
     *   Add the line item total to the order total.
     *   Replace the order in the orderBook list of orders.
     *   Save the order book.
     *   Return the updatedd order.
     *
     *   @param orderBook = order book for client
     *   @param order = existing order
     *   @param orderItem = item to insert in order
     *   @return order = An updated order

     */

    @Override
    public Optional<Order> updateOrder(OrderBook orderBook, Order order, OrderItem orderItem) {

        try {

            Optional<Product> product = retrieveProduct(orderItem.getSkuid());
            if (product.isPresent()) {

                Double lineItemTotal = orderItem.getQuantity() * product.get().getPrice();
                orderItem.setLineItemTotal(lineItemTotal);

                Double orderTotal = order.getOrderTotal();
                orderTotal +=orderItem.getLineItemTotal();


                if (order.getOrderItems() == null) {
                    List<OrderItem> current_order_items = new ArrayList<OrderItem>();
                    current_order_items.add(orderItem);
                    order.setOrderItems(current_order_items);
                } else {
                    order.getOrderItems().add(orderItem);
                }
                order.setOrderTotal(orderTotal);



                List<Order> newOrders = new ArrayList<Order>();
                for(Order _order : orderBook.getOrders()){
                    if (_order.getOrderId() != order.getOrderId()) {
                        newOrders.add(_order);
                    }
                }
                newOrders.add(order);
                orderBook.setOrders(newOrders);


                orderBookDao.save(orderBook);
                return Optional.of(order);

            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Service Helpers - should be implemented somewhere else, usually business domain specific
     * Should not send large objects into helpers but for templating its acceptable
     *
     * "failed-skuid" is not a good idea, but for this example....
     * "failed-orderid" is not a good idea, but for this example....
     *
     * @param product
     * @return String = calculated skuId
     */
    private String getNewSkuid(Product product) {

        try {
            int nxt = ran.nextInt(1000);
            StringBuffer skuid = new StringBuffer(product.getTitle());
            skuid.append("_");
            skuid.append(product.getDose());
            skuid.append("_");
            skuid.append(Integer.toString(nxt));
            return skuid.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "failed-skuid";
        }
    }

    /**
     *
     * @param orderBook
     * @return orderid = calculated orderId
     */
    private String getNewOrderid(OrderBook orderBook) {

        try {
            int nxt = ran.nextInt(1000000);
            StringBuffer orderid = new StringBuffer(orderBook.getClientEmail());
            orderid.append("_");
            orderid.append(Integer.toString(nxt));
            return orderid.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "failed-orderid";
        }
    }

}