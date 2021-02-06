/**
 * <h1>xcelvie product order controller</h1>
 * <p>
 * REST controller to display, update, delete, add products
 * and orders for xcelvie.
 * </p>
 * @TODO View validation is not implemented, assume input is valid from the requesting client (IE all fields input as a minimum)
 * @TODO No security is implemented, assume upstream authenitication has been obtained
 * @TODO No logging is implemmented, e.printstacktrace is on for all exceptions
 * @TODO Implement a View Template mapping strategy before sending to service layer
 *
 * @author  Hamid, Kevin Gerard
 * @version 1.0
 * @since   14-09-2020
 */
package com.kgh.dpiprobe.controllers;

import com.kgh.dpiprobe.models.Order;
import com.kgh.dpiprobe.models.OrderBook;
import com.kgh.dpiprobe.models.OrderItem;
import com.kgh.dpiprobe.models.Product;
import com.kgh.dpiprobe.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dpiprobe")
public class ProductOrdersController {

    @Autowired
    OrderService orderService;

    /*
GET MAPPINGS -----------------------------------------------------------------------------------------------------------------------------------
 */
    /**
     * Products are retrieved if published = true, or by title
     *
     * @param title = name of product
     * @return ResponseEntity<List, HTTPStatus>
     */

    @GetMapping("/products")
    @ApiOperation(value ="List Xcelvie published products, or retrieve by title (containing)")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String title) {
        try {

            List<Product> products;
            if (title == null) {
                products = orderService.getProducts(null);
            } else {
                products = orderService.getProducts(title);
            }
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * skuid should be unique, based on a business logic strategy. For this example, I am building it using a combinnation of fields and a randomm number.
     *
     * @param skuid
     * @return ResponseEntity<Product, HTTPStatus>
     */

    @GetMapping("/products/{skuid}")
    @ApiOperation(value ="List an Xcelvie product with a given skuid")
    public ResponseEntity<Product> getProductBySkuId(@PathVariable("skuid") String skuid) {

        try {

                Optional<Product> productData = orderService.retrieveProduct(skuid);

                if (productData.isPresent()) {
                    return new ResponseEntity<>(productData.get(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/*
POST MAPPINGS -----------------------------------------------------------------------------------------------------------------------------------
 */
    /**
     * Products are inserted with an initial status of published=true
     * @Assume product field validation is performed in the view (minimum fields allowed to be updated with range)
     *
     * @param product
     * @return ResponseEntity<Product, HTTPStatus>
     */

    @PostMapping("/products")
    @ApiOperation(value ="Post a new product into the Xcelvie catalogue")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {

        try {

            Product _product = new Product(
                    product.getTitle(),
                    product.getDescription(),
                    product.getCompound(),
                    product.getClassification(),
                    product.getDose(),
                    product.getPrice()
            );
            if (orderService.createProduct(_product)) {
                return new ResponseEntity<>(_product, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(_product, HttpStatus.NOT_MODIFIED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * First of all, create an order book for a client
     * In this case, add the client email to the order book, if the client email already exists (one book per email)
     * Then just return this orderbook
     *
     * @param orderBook
     * @return ResponseEntity<Order, HTTPStatus>
     */

    @PostMapping("/orderbook")
    @ApiOperation(value ="Build an order book for a client using their email address as a primary key")
    public ResponseEntity<OrderBook> createOrderBook(@RequestBody OrderBook orderBook) {

        try {

                Optional<OrderBook> orderBookData = orderService.retrieveOrderBook(orderBook.getClientEmail());

                if (orderBookData.isPresent()) {
                    return new ResponseEntity<>(orderBookData.get(), HttpStatus.NOT_MODIFIED);
                } else {

                    OrderBook _orderBook = new OrderBook(orderBook.getClientEmail());
                    if (orderService.createOrderBook(_orderBook)) {
                        return new ResponseEntity<>(_orderBook, HttpStatus.CREATED);
                    } else {
                        return new ResponseEntity<>(_orderBook, HttpStatus.NOT_MODIFIED);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }

    /**
     * Create a new order for a client, given the orderbook retrieved using the clientEmail parameter
     *
     * @param clientEmail
     * @return ResponseEntity<Order, HTTPStatus>
     */

    @PostMapping("/orderbook/{clientemail}")
    @ApiOperation(value ="Build a new order for a client")
    public ResponseEntity<Order> createOrder(@PathVariable("clientemail") String clientEmail) {

        try {

            Optional<OrderBook> orderBookData = orderService.retrieveOrderBook(clientEmail);

            if (orderBookData.isPresent()) {

                Optional<Order> orderData = orderService.createOrder(orderBookData.get());

                if (orderData.isPresent()) {
                    return new ResponseEntity<>(orderData.get(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
                }

            } else {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * First of all, retrieve the oderbook for the client
     * Second, retrieve the order in the clients order book
     * Third, insert the order into the orderbook
     *
     * @param clientEmail
     * @param orderId
     *
     * @return ResponseEntity<Order, HTTPStatus>
     */

    @PostMapping("/orderbook/{clientemail}/_/{orderid}")
    @ApiOperation(value ="Add an item to an existing order given a clientemail and an orderid")
    public ResponseEntity<Order> createOrderItem(@PathVariable("clientemail") String clientEmail, @PathVariable("orderid") String orderId, @RequestBody OrderItem orderItem) {

        try {
            Optional<OrderBook> orderBookData = orderService.retrieveOrderBook(clientEmail);

            if (orderBookData.isPresent()) {

                /* Exposing orderBook at the controller - better solution possible */
                OrderBook orderBook = orderBookData.get();
                Optional<Order> orderData = orderService.retrieveOrder(orderBook, orderId);

                if (orderData.isPresent()) {

                    OrderItem _orderItem = new OrderItem(
                            orderItem.getSkuid(),
                            orderItem.getQuantity()
                    );
                    Optional<Order> newOrderData = orderService.updateOrder(orderBook, orderData.get(), _orderItem);

                    if (newOrderData.isPresent()) {

                        return new ResponseEntity<>(newOrderData.get(), HttpStatus.CREATED);

                    } else {
                        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /*
PUT MAPPINGS -----------------------------------------------------------------------------------------------------------------------------------
 */
    /**
     * Update products using the skuid as the candidate key.
     *
     * @param skuid
     * @param product
     * @return ResponseEntity<Product, HTTPStatus>
     */
    @PutMapping("/products/{skuid}")
    @ApiOperation(value ="Update an existing Xcelvie product, retrieved using the unique skuid")
    public ResponseEntity<Product> updateProductWithSkuid(@PathVariable("skuid") String skuid, @RequestBody Product product) {

        try {

                Product _candidate = new Product(
                        product.getTitle(),
                        product.getDescription(),
                        product.getCompound(),
                        product.getClassification(),
                        product.getDose(),
                        product.getPrice()
                );

                Optional<Product> productData = orderService.updateProduct(skuid, _candidate);

                if (productData.isPresent()) {
                    return new ResponseEntity<>(productData.get(), HttpStatus.ACCEPTED);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
                }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
 /*
DELTEE MAPPINGS -----------------------------------------------------------------------------------------------------------------------------------
 */

    /**
     *  Soft deletion set published=false
     *
     * @param skuid
     * @return ResponseEntity<Product, HTTPStatus>
     */
    @DeleteMapping("/products/{skuid}")
    @ApiOperation(value ="Mark an existing Xcelvie product as not published, retrieved using the unique skuid")

    public ResponseEntity<HttpStatus> deleteProductWithSkuid(@PathVariable("skuid") String skuid) {

        try {
                boolean deleted = orderService.deleteProduct(skuid);

                if (deleted) {
                    return new ResponseEntity<>(HttpStatus.ACCEPTED);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
                }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}