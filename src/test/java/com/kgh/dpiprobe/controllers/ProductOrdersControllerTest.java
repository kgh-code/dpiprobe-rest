package com.kgh.dpiprobe.controllers;

import com.kgh.dpiprobe.models.Product;
import com.kgh.dpiprobe.service.OrderService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * Simple high level test suite for teh ProductOrderController
 * Tests response codes returned from the order service
 * and the existence of expected objects (products).
 *
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProductOrdersController.class)
public class ProductOrdersControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService service;

    private Product p = new Product(
            "parotseatemall",
            "a squarky tablet",
            "dihydrideduobromide",
            "painrelief",
            100,
            20.20);

    @Test
    public void test_controller_calls_service_to_delete_products() throws Exception {

        given(service.deleteProduct(anyString())).willReturn(true);

        mvc.perform(delete("/dpiprobe/products/XXX")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }


    @Test
    public void test_controller_retrieves_product_given_a_skuid() throws Exception {

        given(service.retrieveProduct(anyString())).willReturn(java.util.Optional.of(p));

        mvc.perform(get("/dpiprobe/products/XXX")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.classification").value("painrelief"))
                .andExpect(status().isOk());

    }

    @Test
    public void test_controller_posts_a_product_given_a_json_object() throws Exception {

        Gson json = new Gson();
        String json_p = json.toJson(p);

        given(service.createProduct(Mockito.any(Product.class))).willReturn(true);

        mvc.perform(post("/dpiprobe/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json_p)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void test_controller_updates_a_product_given_a_json_object_and_skuid() throws Exception {

        Gson json = new Gson();
        String json_p = json.toJson(p);

        given(service.updateProduct(anyString(), Mockito.any(Product.class))).willReturn(java.util.Optional.of(p));

        mvc.perform(put("/dpiprobe/products/XXX")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json_p))
                .andExpect(status().is2xxSuccessful());
    }
}
