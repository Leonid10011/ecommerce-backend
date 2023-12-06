package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.InventoryDTO;
import com.lbcoding.ecommerce.service.InventoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/inventory")
public class InventoryController {
    @Inject
    InventoryService inventoryController;

    @POST
    @Path("/")
    public Response create(InventoryDTO inventoryDTO){
        return inventoryController.create(inventoryDTO);
    }

}
