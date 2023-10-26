package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.CategoryDTO;
import com.lbcoding.ecommerce.dto.InventoryDTO;
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
     * @param id
     * @return
     */
    public Response updateQuantity(int quantity, Long id, Boolean operation){

        Inventory existingInventory = inventoryRepository.get(id);

        if(existingInventory != null){
            inventoryRepository.update(quantity, id, operation);
            return Response.status(Response.Status.OK).entity("Updated").build();
        }
        return Response.status(Response.Status.CONFLICT).entity("Does not exists").build();
    }

    public Response delete(Long id){
        if(inventoryRepository.get(id) != null){
            inventoryRepository.delete(id);

            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Inventory not found").build();
    }

    public Response get(Long id){
        Inventory inventory = inventoryRepository.get(id);

        if(inventory != null){
            InventoryDTO inventoryDTO = new InventoryDTO(inventory.getQuantity(), inventory.getProductID());

            return Response.status(Response.Status.OK).entity(inventoryDTO).build();
        } else {

            return Response.status(Response.Status.NOT_FOUND).entity(null).build();
        }
    }
}
