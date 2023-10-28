package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.model.Category;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CategoryControllerTest {
    @Test
    public void testCategory(){
        Category category = new Category(
                "TestCategory"
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(category)
                .post("/category/create");

        assertEquals(201, response.getStatusCode());

        Long categoryId = response.jsonPath().getLong("id");

        Response response1 = RestAssured.given()
                .get("/category/get/" + categoryId);

        assertEquals(200, response1.getStatusCode());

        Response response2 = RestAssured.given()
                .delete("/category/delete/" + categoryId);

        assertEquals(204, response2.getStatusCode());
    }
}
