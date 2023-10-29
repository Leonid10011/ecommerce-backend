package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.CredentialDTO;
import com.lbcoding.ecommerce.dto.UserDTO;
import com.lbcoding.ecommerce.service.AuthenticationService;
import com.lbcoding.ecommerce.service.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@Path("/user")
public class UserController {

    @Inject
    UserService userService;

    @Inject
    AuthenticationService authenticationService;

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
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id){
        return userService.deleteUser(id);
    }

    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(CredentialDTO credentialDTO) {
        return authenticationService.login(credentialDTO);
    }
}
