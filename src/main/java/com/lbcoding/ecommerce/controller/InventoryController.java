package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.InventoryDTO;
import com.lbcoding.ecommerce.service.InventoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/inventories")
public class InventoryController {
    @Inject
    InventoryService inventoryController;
    @POST
    @Path("/")
    public Response create(InventoryDTO inventoryDTO){
        return inventoryController.create(inventoryDTO);
    }
    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id){
        return inventoryController.findById(id);
    }
    @GET
    @Path("/product/{id}")
    public Response getByProduct(@PathParam("id") long id){
        return inventoryController.findByProduct(id);
    }
    @GET
    @Path("/findByProductAndSize/")
    public Response getByProductAndSize(InventoryDTO inventoryDTO){
        return inventoryController.findByProductAndSize(inventoryDTO);
    }
    @PUT
    @Path("/")
    public Response update(InventoryDTO inventoryDTO){
        return inventoryController.update(inventoryDTO);
    }
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id){
        return inventoryController.delete(id);
    }
}
