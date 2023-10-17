package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.TransactionDTO;
import com.lbcoding.ecommerce.repository.TransactionRepository;
import com.lbcoding.ecommerce.service.TransactionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/transaction")
public class TransactionController {
    @Inject
    TransactionService transactionService;

    @GET
    @Path("/get/{id}")
    public Response get(@PathParam("id") Long id){
        return transactionService.get(id);
    }

    @GET
    @Path("/get_order/{id}")
    public Response getByOrder(@PathParam("id") Long orderId){
        return transactionService.get(orderId);
    }

    @POST
    @Path("/create")
    public Response create(TransactionDTO transactionDTO){
        return transactionService.create(transactionDTO);
    }

    @DELETE
    @Path("/delete")
    public Response delete(Long id){
        return transactionService.delete(id);
    }
}
