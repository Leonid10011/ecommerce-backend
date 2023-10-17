package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.UserDTO;
import com.lbcoding.ecommerce.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Path("/get")
    public Response get(){
        return userService.getUser();
    }

    @GET
    @Path("/get/{username}")
    public Response get(@PathParam("username") String username){
        return userService.getUser(username);
    }

    @POST
    @Path("/create")
    public Response create(UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @DELETE
    @Path("/delete")
    public Response delete(Long id){
        return userService.deleteUser(id);
    }
}
