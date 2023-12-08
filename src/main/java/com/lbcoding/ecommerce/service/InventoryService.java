package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.InventoryDTO;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.repository.InventoryRepository;
import com.lbcoding.ecommerce.service.inerfaces.IInventoryService;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class InventoryService implements IInventoryService {
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
    @Inject
    InventoryRepository inventoryRepository;
    /**
     * Attempts to create a new inventory
     * @param inventoryDTO DTO containing necessary data to create the inventory
     * @return Success: create inventory with status code 201.
     *  Failure: error message with status code 409
     *  Validation Failure: errorMessage with status code 400
     */
    @Override
    public Response create(InventoryDTO inventoryDTO) {
        Set<String> errorMessages = DTOValidator.validateDTO(inventoryDTO);
        if(!errorMessages.isEmpty()){
            logger.warn("Error validating inventory");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }
        logger.info("Attempting to create inventory");
        Inventory newInventory = inventoryDTOToEntity(inventoryDTO);
        try {
            inventoryRepository.create(newInventory);
            logger.info("Successfully create inventory with ID: " + newInventory.getInventory_id());
            InventoryDTO resDTO = inventoryEntityToDTO(newInventory);
            return Response.status(Response.Status.CREATED).entity(resDTO).build();
        } catch (EntityExistsException e) {
            logger.warn("Entity already exist");
            return Response.status(Response.Status.CONFLICT).entity("Inventory already exist with product, size and location combination").build();
        }
    }

    /**
     * Attempts to find inventory by ID
     * @param id The ID of the inventory to find
     * @return Success: InventoryDTO with status code 200.
     * Failure: errorMessage with status code 404.
     */
    @Override
    public Response findById(long id) {
        logger.info("Received request to find inventory by ID: " + id);
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if(inventory.isPresent()){
            logger.info("Found inventory with ID: " + inventory.get().getInventory_id());
            return Response.status(Response.Status.OK).entity(inventoryEntityToDTO(inventory.get())).build();
        } else {
            String errorMessage = "Inventory not found with ID: " + id;
            logger.info(errorMessage);
            return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
        }
    }

    /**
     * Attempts to find an inventory by
     * @param inventoryDTO 
     * @return A List<InventoryDTO>.
     */
    @Override
    public Response findByProductAndSize(InventoryDTO inventoryDTO) {
        logger.info("Received request to find inventory by product and size");
        List<Inventory> inventoryList = inventoryRepository.findByProductAndSize(inventoryDTO.getProduct_id(), inventoryDTO.getSize_id());
        logger.info("Successfully retrieved inventories");
        List<InventoryDTO> inventoryDTOS = inventoryList.stream().map(
                this::inventoryEntityToDTO
        ).toList();

        return Response.status(Response.Status.OK).entity(inventoryDTOS).build();
    }

    /**
     * @param product_id
     * @return
     */
    @Override
    public Response findByProduct(long product_id) {
        logger.info("Received request to find inventory by product");
        List<Inventory> inventoryList = inventoryRepository.findByProduct(product_id);
        List<InventoryDTO> inventoryDTOS = inventoryList.stream().map(this::inventoryEntityToDTO).toList();
        logger.info("Successfully found inventories for product ID: " + product_id);
        return Response.status(Response.Status.OK).entity(inventoryDTOS).build();
    }

    /**
     * @param inventoryDTO 
     * @return
     */
    @Override
    public Response update(InventoryDTO inventoryDTO) {
        Set<String> errorMessages = DTOValidator.validateDTO(inventoryDTO);
        if(!errorMessages.isEmpty()){
            logger.warn("Failed to validate DTO");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }
        logger.info("Received request to update inventory with ID: " + inventoryDTO.getInventory_id());
        try {
            Inventory newInventory = inventoryDTOToEntity(inventoryDTO);
            inventoryRepository.update(newInventory);
            logger.info("Successfully updated inventory with ID: " + inventoryDTO.getInventory_id());
            InventoryDTO resDTO = inventoryEntityToDTO(newInventory);
            return Response.status(Response.Status.CREATED).entity(resDTO).build();
        } catch( NotFoundException e ) {
            String errorMessage = "Inventory not found with ID: " + inventoryDTO.getInventory_id();
            logger.warn(errorMessage);
            return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
        }
    }

    /**
     * Attempts to delete an inventory by ID
     * @param id the id of the inventory to delete
     * @return Success: status code 204.
     */
    @Override
    public Response delete(long id) {
        logger.info("Received request to delete inventory with ID: " + id);
        inventoryRepository.delete(id);
        logger.info("Successfully deleted inventory with ID: " + id);
        return Response.noContent().build();
    }


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
}
