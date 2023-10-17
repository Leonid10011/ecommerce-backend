package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.OrderHistoryDTO;
import com.lbcoding.ecommerce.model.OrderHistory;
import com.lbcoding.ecommerce.repository.OrdeHistoryRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class OrderHistoryService {
    @Inject
    OrdeHistoryRepository orderHistoryRepository;

    @Inject
    UriInfo uriInfo;

    public Response create(OrderHistoryDTO orderHistoryDTO){
        Set<String> errorMessages = DTOValidator.validateDTO(orderHistoryDTO);

        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }
        // When already one exists, we don't create -> ensuring only 1 entry exists
        OrderHistory existingOrderHistory = orderHistoryRepository.get(orderHistoryDTO.getUserId(), orderHistoryDTO.getOrderId());

        if(existingOrderHistory != null){
            return Response.status(Response.Status.CONFLICT).entity("This combination of user and order already exists").build();
        }

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setUserId(orderHistoryDTO.getUserId());
        orderHistory.setOrderId(orderHistoryDTO.getOrderId());

        orderHistoryRepository.create(orderHistory);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(orderHistory.getId()));

        return Response.created(builder.build()).entity(orderHistory).build();
    }

    public Response get(Long id){
        OrderHistory orderHistory = orderHistoryRepository.get(id);

        if(orderHistory != null){
            return Response.status(Response.Status.OK).entity(orderHistory).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Order History not found").build();
    }

    public Response getByUser(Long id){
        List<OrderHistory> orderHistoryList = orderHistoryRepository.getByUser(id);

        if(!orderHistoryList.isEmpty()){
            return Response.status(Response.Status.OK).entity(orderHistoryList).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Order History not found").build();
    }

    public Response getByProduct(Long id){
        List<OrderHistory> orderHistoryList = orderHistoryRepository.getByUser(id);

        if(!orderHistoryList.isEmpty()){
            return Response.status(Response.Status.OK).entity(orderHistoryList).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Order History not found").build();
    }

    public Response delete(Long id){
        OrderHistory orderHistory = orderHistoryRepository.get(id);

        if(orderHistory != null){
            orderHistoryRepository.delete(id);

            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("OrderHistory not found.").build();
    }
}
