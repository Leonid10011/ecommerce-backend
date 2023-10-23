package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.UserDTO;
import com.lbcoding.ecommerce.model.Address;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class UserControllerTest {
    /**
     * Here we create a User and delete it instantly
     */

    @Test
    public void testCreateUser(){
        UserDTO userDTO = new UserDTO(
                 "Test23",
                 "User",
                 "Mock",
                 "t",
                 "Country",
                 "Street",
                 "TIP",
                 "123456",
                 "l@g.com",
                 1L,
                "test"

        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .post("/user/create");

        assertEquals(201, response.getStatusCode());

        Long userId = response.jsonPath().getLong("id");

        Response response1 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userId)
                .delete("/user/delete");

        assertEquals(204, response1.getStatusCode());


    }

    /**
     * Get all users
     */

    @Test
    public void testGetUsers(){

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("/user/get");

        assertEquals(200, response.getStatusCode());
    }

    /**
     * Create a user, get it by username and then delete
     */
    /*
    @Test

    public void getUser(){
        UserDTO userDTO = new UserDTO(
                "Test",
                "User",
                "Mock",
                "nope",
                "123456",
                "l@g.com",
                1L,
                "test"

        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .post("/user/create");

        assertEquals(201, response.getStatusCode());

        Response response2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("/user/get/Test");

        assertEquals(200, response2.getStatusCode());

        Long userId = response.jsonPath().getLong("id");

        Response response1 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userId)
                .delete("/user/delete");

        assertEquals(204, response1.getStatusCode());

    }

     */
}
