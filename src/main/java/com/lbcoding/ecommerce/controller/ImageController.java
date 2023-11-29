package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.ImageDTO;
import com.lbcoding.ecommerce.service.ImagesService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/images")
public class ImageController {
    @Inject
    ImagesService imagesService;

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") long id){
        return imagesService.getById(id);
    }

    @GET
    @Path("/product/{id}")
    public Response getByProductId(@PathParam("id") long id){
        return imagesService.getByProduct(id);
    }

    @POST
    @Path("/")
    public Response create(ImageDTO imageDTO){
        return imagesService.create(imageDTO);
    }

    @PUT
    @Path("/")
    public Response update(ImageDTO imageDTO){
        return imagesService.update(imageDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id){
        return  imagesService.delete(id);
    }
}
