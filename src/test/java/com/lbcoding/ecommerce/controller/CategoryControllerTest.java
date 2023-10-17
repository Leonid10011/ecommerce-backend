package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.CategoryDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CategoryControllerTest {

    @Test
    public void testGetCategory(){
        CategoryDTO categoryDTO = new CategoryDTO(
                "TestCategory2"
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(categoryDTO)
                .post("/category/create");

        assertEquals(201, response.getStatusCode());

        Response response2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("/category/get/TestCategory2");

        assertEquals(200, response2.getStatusCode());

        Long categoryId = response.jsonPath().getLong("id");

        Response response1 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(categoryId)
                .delete("/category/delete");

        assertEquals(204, response1.getStatusCode());

    }

    @Test
    public void testCreateAndDelete(){
        CategoryDTO categoryDTO = new CategoryDTO(
                "TestCategory3"
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(categoryDTO)
                .post("/category/create");

        assertEquals(201, response.getStatusCode());

        Long categoryId = response.jsonPath().getLong("id");

        Response response1 = RestAssured.given()
                .body(categoryId)
                .delete("/category/delete");

        assertEquals(204, response1.getStatusCode());
    }

    @Test
    public void getAllCategory(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("/category/get");

        assertEquals(200, response.getStatusCode());
    }
}
