package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.FavoriteProductDTO;
import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.model.User;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ProductControllerTest {
    /**
     * Simple Test. Create a product and delete it
     */
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

    /**
     * 1. Create two Products with the same name and make assertion for conflict status.
     * 2. Delete the created first product
     */
    @Test
    public void testProductConflict(){
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

        ProductDTO newProductDTODup = new ProductDTO(
                "TestProduct",
                "For testing purposes",
                99.99,
                1L,
                100,
                "image url"
        );

        Response responseDup = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(newProductDTO)
                .post("/product/create");

        assertEquals(409, responseDup.getStatusCode());

        // Now lets delete
        Long productId = response.jsonPath().getLong("id");

        Response response1 = RestAssured.given()
                .delete("/product/delete/" + productId);

        assertEquals(204, response1.getStatusCode());
    }

    @Test
    public void testFavoritProduct(){
        ProductDTO newProductDTO = new ProductDTO(
                "TESTP1312312313",
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

        Long p1Id = response.jsonPath().getLong("id");

        ProductDTO newProductDTO1 = new ProductDTO(
                "TESTP11331122",
                "For testing purposes",
                99.99,
                1L,
                100,
                "image url"
        );

        Response response1 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(newProductDTO1)
                .post("/product/create");

        assertEquals(201, response1.getStatusCode());

        Long p2Id = response1.jsonPath().getLong("id");

        User user= new User(
                "TestNOW31123212",
                "L@Gmail.com",
                1L,
                "123456"
        );

        Response response2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(user)
                .post("/user/");

        assertEquals(201, response.getStatusCode());

        Long uId = response2.jsonPath().getLong("id");

        FavoriteProductDTO favoriteProductDTO = new FavoriteProductDTO(
                uId,
                p1Id
        );

        Response response3 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(favoriteProductDTO)
                .post("/favoriteItem/");

        assertEquals(201, response3.getStatusCode());

        FavoriteProductDTO favoriteProductDTO2 = new FavoriteProductDTO(
                uId,
                p2Id
        );

        Response response4 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(favoriteProductDTO2)
                .post("/favoriteItem/");

        assertEquals(201, response4.getStatusCode());

        Long fId = response4.jsonPath().getLong("id");

        Long f1Id = response3.jsonPath().getLong("id");

        Response response5 = RestAssured.given()
                .get("/favoriteItem/get/" + uId);

        assertEquals(200, response5.getStatusCode());



        Response response9 = RestAssured.given()
                .delete("/favoriteItem/delete/" + fId);

        assertEquals(204, response9.getStatusCode());

        Response response10 = RestAssured.given()
                .delete("/favoriteItem/delete/" + f1Id);

        assertEquals(204, response10.getStatusCode());

        Response response6 = RestAssured.given()
                .delete("/product/delete/" + p1Id);

        assertEquals(204, response6.getStatusCode());

        Response response7 = RestAssured.given()
                .delete("/product/delete/" + p2Id);

        assertEquals(204, response7.getStatusCode());

        Response response8 = RestAssured.given()
                .delete("/user/delete/" + uId);

        assertEquals(204, response8.getStatusCode());


    }

}
