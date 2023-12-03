package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.SizeDTO;
import com.lbcoding.ecommerce.service.SizesService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/sizes")
public class SizeController {
    @Inject
    SizesService sizesService;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") long id){
        return sizesService.findById(id);
    }

    @GET
    @Path("/{name}")
    public Response findByName(@PathParam("name") String name){
        return sizesService.findByName(name);
    }

    @POST
    @Path("/")
    public Response create(SizeDTO sizeDTO){
        return sizesService.create(sizeDTO);
    }

    @PUT
    @Path("/")
    public Response update(SizeDTO sizeDTO){
        return sizesService.update(sizeDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id){
        return sizesService.delete(id);
    }
}
