package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.OrderDTO;
import com.lbcoding.ecommerce.dto.OrderItemDTO;
import com.lbcoding.ecommerce.dto.ProductWithURLDTO;
import com.lbcoding.ecommerce.model.Order;
import com.lbcoding.ecommerce.model.OrderItem;
import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.repository.InventoryRepository;
import com.lbcoding.ecommerce.repository.OrderItemRepository;
import com.lbcoding.ecommerce.repository.OrderRepository;
import com.lbcoding.ecommerce.repository.ProductRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderItemRepository orderItemRepository;

    @Inject
    InventoryRepository inventoryRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    ProductService productService;

    @Inject
    UriInfo uriInfo;
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(OrderDTO orderDTO){
        Set<String> errorMessages = DTOValidator.validateDTO(orderDTO);

        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        Order existingOrder = orderRepository.getByUser(orderDTO.getUserId());

        if(existingOrder != null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Already exists.").build();
        }

        Order order = new Order();

        order.setUserId(orderDTO.getUserId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus(orderDTO.getStatus());

        orderRepository.create(order);


        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(order.getId()));

        return Response.created(builder.build()).entity(order).build();
    }

    public Response getOrder(Long userId){
        Order order = orderRepository.getByUser(userId);

        if(order != null){
            return Response.status(Response.Status.OK).entity(order).build();
        }
        else return Response.status(Response.Status.BAD_REQUEST).entity("Not Found").build();
    }

    public Response deleteOrder(Long orderId){
        Order order = orderRepository.get(orderId);

        if(order != null){
            orderRepository.delete(order.getId());
            return Response.noContent().build();
        } else return Response.status(Response.Status.BAD_REQUEST).entity("Cannot find orderId").build();
    }

    /**
     * @workflow Get OrderId in front end, then use Order id and product id to fetch addItem
     * @param orderItemDTO
     * @return
     */
    @Transactional
    public Response addItem(OrderItemDTO orderItemDTO){

        Set<String> errorMessages = DTOValidator.validateDTO(orderItemDTO);

        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        OrderItem existingOrderItem = orderItemRepository.getByProduct(orderItemDTO.getProductId());


        if(existingOrderItem != null){
            inventoryRepository.update(orderItemDTO.getQuantity(), orderItemDTO.getProductId());

            return Response.status(Response.Status.OK).entity("Updated quantity").build();
        } else {
            OrderItem orderItem = new OrderItem();

            orderItem.setOrderId(orderItemDTO.getOrderId());
            orderItem.setProductId(orderItemDTO.getProductId());
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPrice(orderItemDTO.getPrice());

            orderItemRepository.create(orderItem);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.build(Long.toString(orderItem.getId()));

            return Response.created(builder.build()).entity(orderItem).build();

        }



    }

    /**
     * @todo Maybe remove list from reposity into single item.
     * @param orderItemId
     * @return
     */
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeItem(Long orderItemId){
        OrderItem orderItem = orderItemRepository.getbById(orderItemId);

        if(orderItem != null){
            orderItemRepository.delete(orderItem.getId());

            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Not found").build();
        }
    }

    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getItems(Long orderId){
        List<OrderItem> orderItemList = orderItemRepository.get(orderId);

        List<ProductWithURLDTO> resItemList = new ArrayList<ProductWithURLDTO>();

        if(!orderItemList.isEmpty()){
            orderItemList.stream().forEach(item -> {
                resItemList.add(productService.get(item.getProductId()).readEntity(ProductWithURLDTO.class));
            });
            return Response.status(Response.Status.OK).entity(resItemList).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
