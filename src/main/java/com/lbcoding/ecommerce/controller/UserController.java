package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.UserDTO;
import com.lbcoding.ecommerce.service.AuthenticationService;
import com.lbcoding.ecommerce.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserController {
    @Inject
    UserService userService;
    @Inject
    AuthenticationService authenticationService;
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
}
