package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.request.ProductsRequestDTO;
import com.lbcoding.ecommerce.service.ProductsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/products")
public class ProductController {
    @Inject
    ProductsService productsService;

    @POST
    @Path("/")
    public Response create(ProductsRequestDTO productsRequestDTO){
        return productsService.create(productsRequestDTO);
    }
}
