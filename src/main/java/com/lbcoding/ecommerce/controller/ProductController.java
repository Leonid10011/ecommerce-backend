package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.dto.ProductWithQuantityDTO;
import com.lbcoding.ecommerce.dto.ProductWithURLDTO;
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

    @POST
    @Path("/create")
    public Response create(ProductDTO productDTO){
        return productService.createProduct(productDTO);
    }

    @POST
    @Path("/createWithQuantity")
    public Response create(ProductWithQuantityDTO productWithQuantityDTO){
        return productService.createProduct(productWithQuantityDTO);
    }

    @POST
    @Path("/createWithURL")
    public Response create(ProductWithURLDTO productWithURLDTO){
        return productService.createProduct(productWithURLDTO);
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id){
        return productService.deleteProduct(id);
    }
}
