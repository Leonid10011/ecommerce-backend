package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.AddressDTO;
import com.lbcoding.ecommerce.model.Address;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class AddressControllerTest {
    @Test
    public void testAddress(){
        AddressDTO addressDTO = new AddressDTO(
                "Rurstraße",
                "Düren",
                "52349",
                "Deutschland"
                );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(addressDTO)
                .post("/address/create");

        assertEquals(201, response.getStatusCode());

        Long addressId = response.jsonPath().getLong("id");

        Response response1 = RestAssured.given()
                .get("/address/get/" + addressId);

        assertEquals(200, response1.getStatusCode());

        Response response2 = RestAssured.given()
                .delete("/address/delete/" + addressId);

        assertEquals(204, response2.getStatusCode());
    }
}
