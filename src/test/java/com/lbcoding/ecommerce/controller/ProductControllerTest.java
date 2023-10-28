package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.ProductDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ProductControllerTest {
    @Test
    public void testProduct(){
        ProductDTO newProductDTO = new ProductDTO(
            "TestProduct",
                "For testing purposes",
                99.99,
                1L,
                100,
                "image url"
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(newProductDTO)
                .post("/product/create");

        assertEquals(201, response.getStatusCode());

        // Now lets delete
        Long productId = response.jsonPath().getLong("id");

        Response response1 = RestAssured.given()
                .delete("/product/delete/" + productId);

        assertEquals(204, response1.getStatusCode());
    }

}
