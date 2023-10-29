package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.CartItemDTO;
import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.dto.UserDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CartItemControllerTest {
    @Test
    public void testCartItem() {
        // create a Product
        ProductDTO productDTO = new ProductDTO(
                "TestProdukt000",
                "Descr",
                99.99,
                1L,
                10,
                "http://test.de"
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(productDTO)
                .post("/product/create");

        assertEquals(201, response.getStatusCode());

        Long productId = response.jsonPath().getLong("id");

        // create a User
        UserDTO userDTO = new UserDTO(
                "TestUser",
                "TestUser@gmail.com",
                1L,
                "123456"
        );

        Response response1 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .post("/user");

        assertEquals(201, response1.getStatusCode());

        Long userId = response1.jsonPath().getLong("id");

        // create cartItem
        CartItemDTO cartItemDTO = new CartItemDTO(
                userId,
                productId,
                5
        );

        Response response2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(cartItemDTO)
                .post("/cartItem");

        assertEquals(201, response2.getStatusCode());

        Long cartItemId = response2.jsonPath().getLong("id");

        // get the Item for testing
        Response response3 = RestAssured.given()
                .get("/cartItem/get/" + userId);

        assertEquals(200, response3.getStatusCode());
        // delete cartItem
        Response response4 = RestAssured.given()
                .delete("/cartItem/delete/" + cartItemId);

        assertEquals(204, response4.getStatusCode());

        // delete user
        Response response5 = RestAssured.given()
                .delete("/user/delete/" + userId);

        assertEquals(204, response5.getStatusCode());

        // delete product
        Response response6 = RestAssured.given()
                .delete("/product/delete/" + productId);

        assertEquals(204, response6.getStatusCode());
    }
}
