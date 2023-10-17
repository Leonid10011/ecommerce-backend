package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.DiscountDTO;
import com.lbcoding.ecommerce.service.DiscountService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/discount")
public class DiscountController {
    @Inject
    DiscountService discountService;

    @GET
    @Path("/get/{id}")
    public Response get(@PathParam("id") Long id){
        return discountService.get(id);
    }

    @GET
    @Path("/get_code/{code}")
    public Response get(@PathParam("code") String code){
        return discountService.get(code);
    }

    @POST
    @Path("/create")
    public Response create(DiscountDTO discountDTO){
        return discountService.create(discountDTO);
    }

    @DELETE
    @Path("/delete")
    public Response delete(Long id){
        return discountService.delete(id);
    }
}
