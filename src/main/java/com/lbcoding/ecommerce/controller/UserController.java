package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.AddressDTO;
import com.lbcoding.ecommerce.dto.OrderDTO;
import com.lbcoding.ecommerce.dto.UserDTO;
import com.lbcoding.ecommerce.dto.UserProfileDTO;
import com.lbcoding.ecommerce.service.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserController {
    @Inject
    UserService userService;
    @Inject
    AuthenticationService authenticationService;
    @Inject
    UserProfileService userProfileService;
    @Inject
    AddressService addressService;
    @Inject
    OrderService orderService;
    @POST
    @Path("/")
    public Response create(UserDTO userDTO){
        return userService.create(userDTO);
    }

    @POST
    @Path("/signin")
    public Response signin(UserDTO userDTO){
        return authenticationService.login(userDTO);
    }

    @POST
    @Path("/profile")
    public Response createProfile(UserProfileDTO userProfileDTO) {
        return userProfileService.create(userProfileDTO);
    }

    @POST
    @Path("/order")
    public Response createOrder(OrderDTO orderDTO){
        return orderService.create(orderDTO);
    }

    @POST
    @Path("/address")
    public Response createAddress(AddressDTO addressDTO) {
        return addressService.create(addressDTO);
    }

    @GET
    @Path("/address/{id}")
    public Response getAddresses(@PathParam("id") long user_id) {
        return addressService.getByUser(user_id);
    }
    @GET
    @Path("/order/byUserId")
    public Response getByUser(@QueryParam("id") long user_id){
        return orderService.getByUser(user_id);
    }
    @PUT
    @Path("/order")
    public Response updateOrderStatus(OrderDTO orderDTO){
        return orderService.updateStatus(orderDTO);
    }
}
