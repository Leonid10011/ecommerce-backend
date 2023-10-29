package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.AddressDTO;
import com.lbcoding.ecommerce.service.AddressService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/address")
public class AddressController {
    @Inject
    private AddressService addressService;

    @GET
    @Path("/get/{id}")
    public Response getAddress(@PathParam("id") Long id){
        return addressService.getAddress(id);
    }

    @POST
    @Path("/create")
    public Response createAddress(AddressDTO addressDTO){
        return addressService.createAddress(addressDTO);
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteAddress(@PathParam("id") Long id){
        return addressService.deleteAddress(id);
    }
}
