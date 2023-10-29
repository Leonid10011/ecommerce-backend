package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.CartItemDTO;
import com.lbcoding.ecommerce.service.CartItemService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/cartItem")
public class CartItemController {
    @Inject
    private CartItemService cartItemService;

    @POST
    @Path("/")
    public Response create(CartItemDTO cartItemDTO){
        return cartItemService.createCartItem(cartItemDTO);
    }

    @GET
    @Path("/get/{userId}")
    public Response getByUser(@PathParam("userId") Long userId){
        return cartItemService.getCartItemsByUser(userId);
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id){
        return  cartItemService.deleteCartItem(id);
    }
}
