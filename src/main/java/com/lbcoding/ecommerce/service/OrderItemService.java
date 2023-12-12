package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.OrderItemDTO;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.model.OrderItem;
import com.lbcoding.ecommerce.model.Size;
import com.lbcoding.ecommerce.model.compositeKey.OrderItemId;
import com.lbcoding.ecommerce.repository.InventoryRepository;
import com.lbcoding.ecommerce.repository.OrderItemRepository;
import com.lbcoding.ecommerce.repository.SizesRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

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
    @Transactional
    public Response create(OrderItemDTO orderItemDTO){
        logger.info("Received request to create order item");
        Optional<Size> size = sizesRepository.findByName(orderItemDTO.getSize_name());
        if (size.isEmpty()){
            logger.warn("Given Size not valid");
            return Response.status(Response.Status.NOT_FOUND).entity("Given Size not valid").build();
        }
        long size_id = size.get().getSize_id();
        Inventory inventory = inventoryRepository.findNearest(orderItemDTO.getProduct_id(), size_id);
        int possible_quantity = inventoryRepository.reduceQuantity(orderItemDTO.getQuantity(), inventory.getInventory_id());

        OrderItem orderItem = orderItemDTOToEntity(orderItemDTO);
        orderItemRepository.create(orderItem);

        return Response.status(Response.Status.CREATED).entity(possible_quantity).build();
    }
    /**
     *  Update the quantity of an order item and also change the corresponding product inventories quantity by doing the following
     *  1. Get old orderItem from the database and retrieve its "old_quantity"
     *  2. Get the id of the size for the ordered Item in order to find the inventory in which to change quantity
     *  3. Depending on if new quantity is bigger or smaller the old one, reduce/ increase inventory quantity
     *  4. Update product
     *  5. Return the quantity that can be ordered
     * @param orderItemDTO DTO containing the new data
     * @return quantity that can be ordered
     */
    @Transactional
    public Response update(OrderItemDTO orderItemDTO){
        Set<String> errorMessage = DTOValidator.validateDTO(orderItemDTO);
        if(!errorMessage.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        logger.info("Received request to update order item for order ID: " + orderItemDTO.getOrder_id() + " product ID: " + orderItemDTO.getProduct_id());

        OrderItemId orderItemId = new OrderItemId(
                orderItemDTO.getOrder_id(),
                orderItemDTO.getProduct_id()
        );
        // 1. Find old order item
        Optional<OrderItem> orderItem = orderItemRepository.findById(orderItemId);
        if(orderItem.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity("OrderItem not found for order ID: " + orderItemDTO.getOrder_id() + " and product ID: " + orderItemDTO.getProduct_id()).build();
        }
        // 2. Get the size id of the provided size of orderd item
        Optional<Size> size = sizesRepository.findByName(orderItemDTO.getSize_name());
        if(size.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity("Size with name: " + orderItemDTO.getSize_name() + " not found. Aborting updating order item").build();
        }
        long size_id = size.get().getSize_id();

        Inventory inventory;
        try {
            inventory = inventoryRepository.findNearest(orderItemDTO.getProduct_id(), size_id);
        } catch ( NotFoundException e ){
            return Response.status(Response.Status.NOT_FOUND).entity("No inventory found for product ID: " + orderItemDTO.getProduct_id()).build();
        }
        // 3. Change inventories quantity
        int old_quantity = orderItem.get().getQuantity();
        int new_quantity = orderItemDTO.getQuantity();
        int diff = Math.abs(old_quantity - new_quantity);

        logger.info(old_quantity + " - old || new - " + new_quantity);

        int res;
        // if new quantity is bigger than old, reduce inventory quantity
        if(old_quantity < new_quantity){
            // if reducing inventory quantity drops below 0,
            res = inventoryRepository.reduceQuantity(diff, inventory.getInventory_id()) + old_quantity;
        } else if( new_quantity < old_quantity){
            inventoryRepository.increaseQuantity(diff, inventory.getInventory_id());
            res = new_quantity;
        } else {
            res = new_quantity;
        }
        // 4. Finally, updated product
        OrderItem newOrderItem = orderItemDTOToEntity(orderItemDTO);
        newOrderItem.setQuantity(res);
        orderItemRepository.update(newOrderItem);
        // 5. Return the quantity that can be ordered
        return Response.status(Response.Status.OK).entity(res).build();
    }

    OrderItem orderItemDTOToEntity(OrderItemDTO orderItemDTO){
        OrderItemId orderItemId = new OrderItemId(
                orderItemDTO.getOrder_id(),
                orderItemDTO.getProduct_id()
        );

        return new OrderItem(
                orderItemId,
                orderItemDTO.getSize_name(),
                orderItemDTO.getQuantity(),
                orderItemDTO.getSubtotal()
        );
    }
}
