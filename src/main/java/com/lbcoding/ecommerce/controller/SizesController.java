package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.request.SizesRequestDTO;
import com.lbcoding.ecommerce.model.Sizes;
import com.lbcoding.ecommerce.service.SizesService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/sizes")
public class SizesController {
    @Inject
    SizesService sizesService;

    @POST
    @Path("/")
    public Response create(SizesRequestDTO size){
        return sizesService.create(size);
    }

    @GET
    @Path("/")
    public Response get(){
        return sizesService.findAll();
    }
}
