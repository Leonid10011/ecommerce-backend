package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.InventoryDTO;
import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.exception.InsufficientInventoryException;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.repository.InventoryRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import com.mysql.cj.x.protobuf.Mysqlx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class InventoryService {
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
    @Inject
    InventoryRepository inventoryRepository;

    /**
     * Set the quantity for a product in Inventory.
     * @param inventoryDTO
     * @return
     */
    public Response setQuantity(InventoryDTO inventoryDTO){
        logger.info("Received request to set quantity for product ID: " + inventoryDTO.getProductID());
        Set<String> errorMessages = DTOValidator.validateDTO(inventoryDTO);

        if(!errorMessages.isEmpty()){
            logger.info("Validation failed for inventoryDTO with product ID: " + inventoryDTO.getProductID());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorMessages)
                    .build();
        }

        Inventory newInventory = mapInventoryDTOToInventoryEntity(inventoryDTO);

        inventoryRepository.setQuantity(newInventory.getProductId(), newInventory.getQuantity());
        logger.info("Completed setting quantity for product ID: " + inventoryDTO.getProductID());
        return Response.status(Response.Status.OK).entity(newInventory).build();
    }
    private Inventory mapInventoryDTOToInventoryEntity(InventoryDTO inventoryDTO){
        return new Inventory(
                inventoryDTO.getQuantity(),
                inventoryDTO.getProductID()
        );
    }
    /**
     * Update the quantity for a product in the inventory
     * @param quantity
     * @param productId
     * @return
     */
    public Response incrementQuantity(Long productId, int quantity){
        logger.info("Received request to increment quantity by "+ quantity + " for product ID: " + productId);
        inventoryRepository.incrementQuantity(productId, quantity);
        Inventory updatedInventory = inventoryRepository.findByProductId(productId).orElse(null);
        logger.info("Incremented quantity of product ID: " + productId + " successfully");
        return Response.status(Response.Status.OK).entity(updatedInventory).build();
    }

    /**
     * Decrements the quantity of a product in the inventory by `quantity`.
     * If insufficient, send a response indicating that it cannot decrement and sends the current stock quantity in inventory.
     * @param productId
     * @param quantity
     * @return
     */
    public Response decrementQuantity(Long productId, int quantity){
        logger.info("Received request to decrement quantity by "+ quantity + " for product ID: " + productId);
        try {
            inventoryRepository.decrementQuantity(productId, quantity);
        } catch( InsufficientInventoryException e) {
            logger.info("Insufficient inventory for quantity " + quantity);
            Map<String, String> errorResponse =  new HashMap<>();
            errorResponse.put("error", "insufficient inventory");
            errorResponse.put("message", "The requested quantity exceeds the available stock. Please reduce the quantity. Available stock: " + e.getAvailableQuantity());
            return Response.status(422)
                    .entity(errorResponse)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
        Inventory updateInventory = inventoryRepository.findByProductId(productId).orElse(null);
        logger.info("Completed decrementing the quantity by " + quantity + " for product ID: " + productId);
        return Response.status(Response.Status.OK).entity(updateInventory).build();
    }

    /**
     * Attempts to delete an inventory.
     * @param id
     * @return noContent on Success. StatusCode NOT_FOUND on failure.
     */
    public Response delete(Long id){
        logger.info("Received request to delete inventory with ID: " + id);
        try {
            inventoryRepository.delete(id);
            logger.info("Inventory deleted successfully with ID: " + id);
            return Response.noContent().build();
        } catch( NotFoundException e) {
            logger.warn("Inventory not found. Nothing to do.");
            String errorMessage = "Inventory not found by ID: " + id;
            return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
        }
    }

    /**
     * Finds an inventory with the provided product ID.
     * @param productId
     * @return Inventory on success, otherwise null
     */
    public Response findByProductId(Long productId){
        logger.info("Received request to find inventory by product ID: " + productId);
        Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);
        if(inventory.isPresent()){
            Inventory item = inventory.get();
            InventoryDTO inventoryDTO = new InventoryDTO(item.getQuantity(), item.getProductId());
            logger.info("Inventory found successfully with product ID: " + productId);
            return Response.status(Response.Status.OK).entity(inventoryDTO).build();
        }
        String message = "Inventory not found with product ID: " + productId;
        logger.warn(message);
        return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    }

    /**
     * Find inventory by ID.
     * @param id
     * @return
     */
    public Response findById(Long id){
        logger.info("Received request to find inventory by ID: " + id);
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if(inventory.isPresent()){
            InventoryDTO inventoryDTO = mapInventoryEntityToInventoryDTO(inventory.get());
            return Response.status(Response.Status.OK).entity(inventoryDTO).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Simple helper function.
     * @param inventory
     * @return
     */
    private InventoryDTO mapInventoryEntityToInventoryDTO(Inventory inventory){
        return new InventoryDTO(
                inventory.getQuantity(),
                inventory.getProductId()
        );
    }
}
