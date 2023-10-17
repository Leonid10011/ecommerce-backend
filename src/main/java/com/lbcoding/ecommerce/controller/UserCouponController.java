package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.UserCouponDTO;
import com.lbcoding.ecommerce.repository.UserCouponRepository;
import com.lbcoding.ecommerce.service.UserCouponService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/user_coupon")
public class UserCouponController {
    @Inject
    UserCouponService userCouponService;

    @GET
    @Path("/get_user/{id}")
    public Response getByUser(@PathParam("id") Long id){
        return userCouponService.getByUser(id);
    }

    @GET
    @Path("/get_discount/{id}")
    public Response getByDiscount(@PathParam("id") Long id){
        return userCouponService.getByDiscount(id);
    }

    @GET
    @Path("/get/{id}")
    public Response get(@PathParam("id") Long id){
        return userCouponService.get(id);
    }

    @POST
    @Path("/create")
    public Response create(UserCouponDTO userCouponDTO){
        return userCouponService.create(userCouponDTO);
    }

    @DELETE
    @Path("/delete")
    public Response delete(Long id) {
        return userCouponService.delete(id);
    }
}
