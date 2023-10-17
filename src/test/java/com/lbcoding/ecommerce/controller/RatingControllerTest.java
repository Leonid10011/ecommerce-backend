package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.RatingDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class RatingControllerTest {
    @Test
    public void testCreate(){
        RatingDTO ratingDTO = new RatingDTO(
                5,
                "test",
                new Date(),
                51L,
                3L
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(ratingDTO)
                .post("/rating/create");

        assertEquals(201, response.getStatusCode());

        Response response1 = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("/rating/get_user/51");

        assertEquals(200, response1.getStatusCode());

        Response response2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("/rating/get_product/3");

        assertEquals(200, response2.getStatusCode());

        Long ratingId = response.jsonPath().getLong("id");

        Response response3 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(ratingId)
                .delete("/rating/delete");

        assertEquals(204, response3.getStatusCode());

    }
}
