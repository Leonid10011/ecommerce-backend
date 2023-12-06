package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.InventoryDTO;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.repository.InventoryRepository;
import com.lbcoding.ecommerce.service.inerfaces.IInventoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class InventoryService implements IInventoryService {
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
    @Inject
    InventoryRepository inventoryRepository;
    /**
     * @param
     * @return
     */
    /*
    public Response create(InventoryDTO inventoryDTO) {
        Inventory inventory = inventoryDTOToEntity(inventoryDTO);
        try {
            inventoryRepository.create(inventory);
            return Response.status(Response.Status.CREATED).entity(inventory).build();
        } catch( Exception e){
            logger.error("Exception creating inventory");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }*/

    private InventoryDTO inventoryEntityToDTO(Inventory inventory){
        return new InventoryDTO(
                inventory.getInventory_id(),
                inventory.getProduct_id(),
                inventory.getSize_id(),
                inventory.getQuantity(),
                inventory.getLocation()
        );
    }

    private Inventory inventoryDTOToEntity(InventoryDTO inventoryDTO){
        return new Inventory(
                inventoryDTO.getInventory_id(),
                inventoryDTO.getProduct_id(),
                inventoryDTO.getSize_id(),
                inventoryDTO.getQuantity(),
                inventoryDTO.getLocation()
        );
    }

    /**
     * @param inventoryDTO 
     * @return
     */
    @Override
    public Response create(InventoryDTO inventoryDTO) {
        return null;
    }
}
