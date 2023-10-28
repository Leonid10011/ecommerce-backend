package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.*;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.model.Order;
import com.lbcoding.ecommerce.repository.ProductImageRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Application;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@Transactional
public class OrderControllerTest {

    @Test
    @Transactional
    public void testOrder(){
        // Create a new Order for a User
        OrderDTO orderDTO = new OrderDTO(
                1551L,
                (new Date()),
                "Test"
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(orderDTO)
                .post("/order/create");
        assertEquals(201, response.getStatusCode());

        // Create a Test Product for test Purposes
        ProductDTO productWithURLDTO = new ProductDTO(
                "Test",
                "For Testing",
                99.99,
                1L,
                10,
                "No URL yet"
        );

        Response response1 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(productWithURLDTO)
                .post("/product/create");
        System.out.println("Create Product");
        assertEquals(201, response1.getStatusCode());

        Long productId = response1.jsonPath().getLong("id");
        Double productPrice = response1.jsonPath().getDouble("price");
        // Create a new OrderItem to add to the Cart
        Long orderId = response.jsonPath().getLong("id");
        OrderItemDTO orderItemDTO = new OrderItemDTO(
                orderId,
                productId,
                5,
                productPrice

        );

        Response response2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(orderItemDTO)
                .post("/order/addItem");

        assertEquals(201, response2.getStatusCode());

        // Check quantity of product in Inventory, must be 10-5
        Response response3 = RestAssured.given()
                .get("/product/" + productId);

        if(response3.getStatusCode() == 200){
            ProductDTO productWithURLDTO1 = response3.as(ProductDTO.class);

            int quantity = productWithURLDTO1.getQuantity();

            assertEquals(5, quantity);
        }

        Long orderItemId = response2.jsonPath().getLong("id");

        // Now we delete the item
        Response response4 = RestAssured.given()
                .body(orderItemDTO)
                .delete("/order/deleteItem/" + orderItemId);

        assertEquals(204, response4.getStatusCode());
        // delete the product
        Response response5 = RestAssured.given()
                .delete("/product/delete/" + productId);
        // Remove order
        Long id = response.jsonPath().getLong("id");

        Response responseP = RestAssured.given()
                .delete("/order/delete/" + id);

        assertEquals(204, responseP.getStatusCode());

    }

}
