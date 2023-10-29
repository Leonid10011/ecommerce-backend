package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.AddressDTO;
import com.lbcoding.ecommerce.dto.UserDTO;
import com.lbcoding.ecommerce.dto.UserProfileDTO;
import com.lbcoding.ecommerce.model.User;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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

    @Test
    public void testUserProfile(){
        UserDTO user= new UserDTO(
                "Leonidas",
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

        AddressDTO addressDTO = new AddressDTO(
                "Am Stadtpark",
                "Düren",
                "52349",
                "Deutschland"
        );

        Response response1 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(addressDTO)
                .post("/address/create");

        assertEquals(201, response1.getStatusCode());

        Long addressId = response1.jsonPath().getLong("id");

        UserProfileDTO userProfileDTO = new UserProfileDTO(
                userId,
                addressId,
                "Leonid",
                "Budkov",
                LocalDate.of(1991,1,10)
        );

        Response response2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userProfileDTO)
                .post("/user/createProfile");

        assertEquals(201, response2.getStatusCode());

        Long profileId = response2.jsonPath().getLong("id");

        Response response3 = RestAssured.given()
                .delete("/user/deleteProfile/" + profileId);

        assertEquals(204, response3.getStatusCode());

        Response response4 = RestAssured.given()
                .delete("/address/delete/" + addressId);

        assertEquals(204, response4.getStatusCode());

        Response response5 = RestAssured.given()
                .delete("/user/delete/" + userId);

        assertEquals(204, response5.getStatusCode());

    }
}
