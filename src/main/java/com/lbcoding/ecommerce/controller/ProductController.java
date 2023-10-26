package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/product")
public class ProductController {

    @Inject
    ProductService productService;

    @GET
    @Path("/get")
    public Response get(){
        return productService.getProducts();
    }

    @GET
    @Path("/get/{id}")
    public Response get(@PathParam("id") Long id){
        return productService.get(id);
    }

    @GET
    @Path("/get/{name}")
    public Response getName(@PathParam("name") String name) { return productService.getSearchName(name);}
    @POST
    @Path("/create")
    public Response create(ProductDTO productDTO){
        return productService.createProduct(productDTO);
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id){
        return productService.deleteProduct(id);
    }
}
