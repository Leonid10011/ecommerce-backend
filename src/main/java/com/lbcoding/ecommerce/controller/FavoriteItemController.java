package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.FavoriteProductDTO;
import com.lbcoding.ecommerce.repository.FavoriteProductRepository;
import com.lbcoding.ecommerce.service.FavoriteProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/favoriteItem")
public class FavoriteItemController {
    @Inject
    private FavoriteProductService favoriteProductService;

    @POST
    @Path("/")
    public Response create(FavoriteProductDTO favoriteProductDTO){
        return favoriteProductService.createFavoriteProduct(favoriteProductDTO);
    }

    @GET
    @Path("/getByUser/{userId}")
    public  Response get(@PathParam("userId") Long userId){
        return favoriteProductService.getByUserId(userId);
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id){
        return favoriteProductService.delete(id);
    }

    @DELETE
    @Path("/delete")
    public Response deleteUP(FavoriteProductDTO favoriteProductDTO){
        return favoriteProductService.deleteByUserAndProduct(favoriteProductDTO.getUserId(), favoriteProductDTO.getProductId());
    }
}
