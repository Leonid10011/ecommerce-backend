package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.OrderDTO;
import com.lbcoding.ecommerce.dto.OrderItemDTO;
import com.lbcoding.ecommerce.service.OrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/order")
public class OrderController {

    @Inject
    OrderService orderService;

    @GET
    @Path("/get/{id}")
    public Response get(@PathParam("id") Long id){
        return orderService.getOrder(id);
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id){
        return orderService.deleteOrder(id);
    }

    @POST
    @Path("/create")
    public Response create(OrderDTO orderDTO){
        return orderService.createOrder(orderDTO);
    }

    @POST
    @Path("/addItem")
    public Response add(OrderItemDTO orderItemDTO){
        return orderService.addItem(orderItemDTO);
    }

    @DELETE
    @Path("/deleteItem/{id}")
    public Response deleteItem(@PathParam("id") Long id){
        return orderService.removeItem(id);
    }




}
