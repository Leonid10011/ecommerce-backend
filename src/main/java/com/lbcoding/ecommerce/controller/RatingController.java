package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.RatingDTO;
import com.lbcoding.ecommerce.service.RatingService;
import com.lbcoding.ecommerce.service.RoleService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/rating")
public class RatingController {
    @Inject
    RatingService ratingService;

    @GET
    @Path("/get_user/{id}")
    public Response getByUser(@PathParam("id") Long id){
        return ratingService.getByUser(id);
    }

    @GET
    @Path("/get_product/{id}")
    public Response getByProduct(@PathParam("id") Long id){
        return ratingService.getByProduct(id);
    }

    @POST
    @Path("/")
    public Response create(RatingDTO ratingDTO){
        return ratingService.create(ratingDTO);
    }

    @PUT
    @Path("/updateRatingValue")
    public Response updateRatingValue(RatingDTO ratingDTO) {
        return ratingService.updateRatingValue(ratingDTO);
    }
    @PUT
    @Path("/updateRatingText")
    public Response updateRatingText(RatingDTO ratingDTO) {
        return ratingService.updateRatingText(ratingDTO);
    }

    @DELETE
    @Path("/delete")
    public Response delete(Long id){
        return ratingService.delete(id);
    }
}
