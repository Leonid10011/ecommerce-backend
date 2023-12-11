package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.OrderItemDTO;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.model.Size;
import com.lbcoding.ecommerce.repository.InventoryRepository;
import com.lbcoding.ecommerce.repository.OrderItemRepository;
import com.lbcoding.ecommerce.repository.SizesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@ApplicationScoped
public class OrderItemService {
    final static Logger logger = LoggerFactory.getLogger(OrderItemService.class);
    @Inject
    SizesRepository sizesRepository;
    @Inject
    OrderItemRepository orderItemRepository;
    @Inject
    InventoryRepository inventoryRepository;

    /**
     * Creates an order item DTO and reduces the respective quantity in the inventory
     * @param orderItemDTO the DTO
     * @return Response containing the possible quantity that can be ordered for this product.
     */
    public Response create(OrderItemDTO orderItemDTO){
        Optional<Size> size = sizesRepository.findByName(orderItemDTO.getSize_name());
        if (size.isEmpty()){
            logger.warn("Given Size not valid");
            return Response.status(Response.Status.NOT_FOUND).entity("Given Size not valid").build();
        }
        long size_id = size.get().getSize_id();
        Inventory inventory = inventoryRepository.findNearest(orderItemDTO.getProduct_id(), size_id);
        int possible_quantity = inventoryRepository.reduceQuantity(orderItemDTO.getQuantity(), inventory.getInventory_id());

        return Response.status(Response.Status.CREATED).entity(possible_quantity).build();
    }
}
