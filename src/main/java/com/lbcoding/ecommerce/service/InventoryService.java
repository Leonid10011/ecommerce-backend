package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.InventoryDTO;
import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.repository.InventoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class InventoryService {

    @Inject
    InventoryRepository inventoryRepository;

    /**
     * Set the quantity for a product in Inventory
     * @param inventoryDTO
     * @return
     */
    public Response setQuantity(InventoryDTO inventoryDTO){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<InventoryDTO>> violations = validator.validate(inventoryDTO);

        if(!violations.isEmpty()){
            List<String> errorMessages = violations.stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.toList());

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorMessages)
                    .build();
        }

        Inventory newInventory = new Inventory();

        newInventory.setQuantity(inventoryDTO.getQuantity());
        newInventory.setProductID(inventoryDTO.getProductID());

        inventoryRepository.setQuantity(newInventory.getQuantity(), newInventory.getProductID());
        System.out.print("INVENTORY");
        return Response.ok(Response.Status.CREATED).entity(newInventory).build();
    }

    /**
     * Update the quantity for a product in the inventory
     * @param quantity
     * @param productId
     * @return
     */
    public Response updateQuantity(int quantity, Long productId, Boolean operation){

        Optional<Inventory> existingInventory = inventoryRepository.findByProductId(productId);

        if(existingInventory.isPresent()){
            inventoryRepository.update(quantity, productId, operation);
            return Response.status(Response.Status.OK).entity("Updated").build();
        }
        return Response.status(Response.Status.CONFLICT).entity("Does not exists").build();
    }

    public Response delete(Long productId){
        if(inventoryRepository.findByProductId(productId).isPresent()){
            inventoryRepository.delete(productId);

            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Inventory not found").build();
    }

    public Response get(Long productId){
        Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);

        if(inventory.isPresent()){
            InventoryDTO inventoryDTO = new InventoryDTO(inventory.get().getQuantity(), inventory.get().getProductID());

            return Response.status(Response.Status.OK).entity(inventoryDTO).build();
        } else {

            return Response.status(Response.Status.NOT_FOUND).entity(null).build();
        }
    }

    public InventoryDTO createInventoryDTO(ProductDTO productDTO){
        return new InventoryDTO(
                productDTO.getQuantity(),
                productDTO.getId()
        );
    }
    public Optional<Inventory> getInventoryByProductId(Long productId){
        return inventoryRepository.findByProductId(productId);
    }
    public Response handleInventory(ProductDTO productDTO, Long productId) {
        Optional<Inventory> existingInventory = inventoryRepository.findByProductId(productId);
        Response quantityResponse;

        if (existingInventory.isPresent()) {
            quantityResponse = updateQuantity(productDTO.getQuantity(), existingInventory.get().getId(), true);
        } else {
            InventoryDTO inventoryDTO = createInventoryDTO(productDTO);
            quantityResponse = setQuantity(inventoryDTO);
        }

        return quantityResponse;
    }
}
