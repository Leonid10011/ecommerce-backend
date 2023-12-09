package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.RatingDTO;
import com.lbcoding.ecommerce.dto.request.ProductsRequestDTO;
import com.lbcoding.ecommerce.service.ProductsService;
import com.lbcoding.ecommerce.service.RatingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/products")
public class ProductController {
    @Inject
    ProductsService productsService;
    @Inject
    RatingService ratingService;
    @POST
    @Path("/")
    public Response create(ProductsRequestDTO productsRequestDTO){
        return productsService.create(productsRequestDTO);
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id){
        return productsService.getById(id);
    }

    @GET
    @Path("/")
    public Response getAll(
        @QueryParam("page") int page,
        @QueryParam("pageSize") int pageSize){

        return productsService.getAll(page, pageSize);
    }

    @GET
    @Path("/getByName")
    public Response getByName(@PathParam("name") String name){
        return productsService.getByName(name);
    }

    @POST
    @Path("/rating")
    public Response createRating(RatingDTO ratingDTO){
        return ratingService.create(ratingDTO);
    }

    @GET
    @Path("/rating/{id}")
    public Response getRatingValueForProduct(@PathParam("id") long id){
        return ratingService.getRatingValue(id);
    }
    @GET
    @Path("/rating/getAll/{id}")
    public Response getRatingsForProduct(@PathParam("id") long id){
        return ratingService.getRatingsForProduct(id);
    }
}
