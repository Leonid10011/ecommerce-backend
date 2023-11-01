package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.service.ProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/product")
public class ProductController {

    @Inject
    ProductService productService;

    // Retrieve all products
    @GET
    @Path("/get")
    public Response get() {
        return productService.getProducts();
    }

    // Retrieve a product by ID
    @GET
    @Path("/get/{id}")
    public Response get(@PathParam("id") Long id) {
        return productService.getByProductId(id);
    }

    // Search for products by name
    @GET
    @Path("/getByName/{name}")
    public Response getByName(@PathParam("name") String name) {
        return productService.getSearchName(name);
    }

    // Create a new product
    @POST
    @Path("/create")
    public Response create(ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    // Delete a product by ID
    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        return productService.deleteProduct(id);
    }
}

