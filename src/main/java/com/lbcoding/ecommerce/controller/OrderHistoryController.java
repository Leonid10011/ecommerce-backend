package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.OrderHistoryDTO;
import com.lbcoding.ecommerce.service.OrderHistoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/OrderHistory")
public class OrderHistoryController {
    @Inject
    OrderHistoryService orderHistoryService;

    @GET
    @Path("/get/{id}")
    public Response get(@PathParam("id") Long id){
        return orderHistoryService.get(id);
    }

    @GET
    @Path("/get_user/{id}")
    public Response getByUser(Long id){
        return orderHistoryService.get(id);
    }

    @GET
    @Path("/get_order/{id}")
    public Response getByOrder(Long id){
        return orderHistoryService.get(id);
    }

    @POST
    @Path("/create")
    public Response create(OrderHistoryDTO orderHistoryDTO){
        return orderHistoryService.create(orderHistoryDTO);
    }

    @DELETE
    @Path("/delete")
    public Response delete(Long id){
        return orderHistoryService.delete(id);
    }
}
