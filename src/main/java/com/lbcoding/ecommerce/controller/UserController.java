package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.AddressDTO;
import com.lbcoding.ecommerce.dto.UserDTO;
import com.lbcoding.ecommerce.dto.UserProfileDTO;
import com.lbcoding.ecommerce.service.AddressService;
import com.lbcoding.ecommerce.service.AuthenticationService;
import com.lbcoding.ecommerce.service.UserProfileService;
import com.lbcoding.ecommerce.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
    @Path("/address")
    public Response createAddress(AddressDTO addressDTO) {
        return addressService.create(addressDTO);
    }

    @GET
    @Path("/address/{id}")
    public Response getAddresses(@PathParam("id") long user_id) {
        return addressService.getByUser(user_id);
    }
}
