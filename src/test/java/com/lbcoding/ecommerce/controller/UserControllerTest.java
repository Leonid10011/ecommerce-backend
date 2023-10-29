package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.model.User;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class UserControllerTest {
    @Test
    public void testUser(){
        User user= new User(
                "Leonid",
                "L@Gmail.com",
                1L,
                "123456"
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(user)
                .post("/user/create");

        assertEquals(201, response.getStatusCode());

        Long userId = response.jsonPath().getLong("id");

        Response response1 = RestAssured.given()
                .delete("/user/delete/" + userId);

        assertEquals(204, response1.getStatusCode());
    }
}
