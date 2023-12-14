package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.OrderDTO;
import com.lbcoding.ecommerce.model.Order;
import com.lbcoding.ecommerce.repository.OrderRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class OrderService {
    final static Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Inject
    OrderRepository orderRepository;

    /**
     * Attempts to create a new order
     * @param orderDTO DTO containing order data  to persist
     * @return Success: orderDTO with status code 201
     * Error: ValidationMessage with status code 400
     */
    @Transactional
    public Response create(OrderDTO orderDTO){
        Set<String> errorMessages = DTOValidator.validateDTO(orderDTO);
        if(!errorMessages.isEmpty()){
            logger.warn("Validation Error");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }
        logger.info("Received request to create order for user ID: " + orderDTO.getUser_id());
        Order order = orderDTOToEntity(orderDTO);
        try {
            orderRepository.create(order);
            OrderDTO resDTO = orderEntityToDTO(order);
            return  Response.status(Response.Status.CREATED).entity(resDTO).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch ( NotFoundException e ){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    /**
     * Attempts to search for orders for a user
     * @param user_id id of the user
     * @return list of order DTOs
     */
    @Transactional
    public Response getByUser(long user_id){
        logger.info("Received request to find orders for user ID: " + user_id);
        List<Order> orders = orderRepository.findByUser(user_id);
        List<OrderDTO> resDTO = orders.stream().map(this::orderEntityToDTO).toList();

        return Response.status(Response.Status.OK).entity(resDTO).build();
    }

    /**
     * Attempts to update the status of an order
     * @param orderDTO contains status text and order id
     * @return Success: update orderDTO with status code 200
     * Error: error message with status code 404
     */
    @Transactional
    public Response updateStatus(OrderDTO orderDTO){
        logger.info("Received request to update order status od order ID: " + orderDTO.getOrder_id());
        try {
            orderRepository.updateStatus(orderDTOToEntity(orderDTO));
            return Response.status(Response.Status.OK).entity(orderDTO).build();
        } catch ( NotFoundException e ){
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found with ID: " + orderDTO.getOrder_id()).build();
        }
    }
    Order orderDTOToEntity(OrderDTO orderDTO){
        return new Order(
                orderDTO.getOrder_id(),
                orderDTO.getUser_id(),
                orderDTO.getCreation_date(),
                orderDTO.getStatus()
        );
    }

    OrderDTO orderEntityToDTO(Order order){
        return new OrderDTO(
                order.getOrder_id(),
                order.getUser_id(),
                order.getCreation_date(),
                order.getStatus()
        );
    }
}
