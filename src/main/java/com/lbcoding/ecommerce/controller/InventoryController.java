package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.InventoryDTO;
import com.lbcoding.ecommerce.service.InventoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/inventories")
public class InventoryController {
    @Inject
    InventoryService inventoryService;
    @POST
    @Path("/")
    public Response create(InventoryDTO inventoryDTO){
        return inventoryService.create(inventoryDTO);
    }
    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id){
        return inventoryService.findById(id);
    }
    @GET
    @Path("/product/{id}")
    public Response getByProduct(@PathParam("id") long id){
        return inventoryService.findByProduct(id);
    }
    @GET
    @Path("/findByProductAndSize/")
    public Response getByProductAndSize(
            @QueryParam("productID") long product_id,
            @QueryParam("sizeId") long size_id
    ){
        return inventoryService.findByProductAndSize(product_id, size_id);
    }
    @PUT
    @Path("/")
    public Response update(InventoryDTO inventoryDTO){
        return inventoryService.update(inventoryDTO);
    }
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id){
        return inventoryService.delete(id);
    }
}
