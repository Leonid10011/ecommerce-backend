package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.DiscountDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class DiscountControllerTest {
    @Test
    public void testCreate(){
        DiscountDTO discountDTO = new DiscountDTO(
                "CODE5",
                "DESCRIPTION",
                "TYPE",
                1.00,
                new Date(),
                new Date()
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(discountDTO)
                .post("/discount/create");

        assertEquals(201, response.getStatusCode());

        Response response1 = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("/discount/get_code/code5");

        assertEquals(200, response1.getStatusCode());

        Long ratingId = response.jsonPath().getLong("id");

        Response response3 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(ratingId)
                .delete("/discount/delete");

        assertEquals(204, response3.getStatusCode());

    }
}
