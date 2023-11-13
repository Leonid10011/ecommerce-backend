package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.OrderDTO;
import com.lbcoding.ecommerce.dto.OrderItemDTO;
import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.dto.response.OrderItemResponseDTO;
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
import java.util.List;
import java.util.Optional;
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
    public Response createOrder(OrderDTO orderDTO) {
        Set<String> errorMessages = DTOValidator.validateDTO(orderDTO);

        if (!errorMessages.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        Optional<Order> existingOrder = orderRepository.getOrderByUser(orderDTO.getUserId());

        if (existingOrder.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Order already exists for this user.").build();
        }

        Order order = createOrderFromDTO(orderDTO);
        orderRepository.create(order);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(order.getId()));

        return Response.created(builder.build()).entity(order).build();
    }

    private Order createOrderFromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus(orderDTO.getStatus());
        return order;
    }

    public Response getOrder(Long userId) {
        Optional<Order> order = orderRepository.getOrderByUser(userId);

        if (order.isPresent()) {
            return Response.status(Response.Status.OK).entity(order.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
        }
    }

    public Response deleteOrder(Long orderId) {
        Optional<Order> order = orderRepository.getOrderById(orderId);

        if (order.isPresent()) {
            orderRepository.deleteOrder(order.get().getId());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
        }
    }

    @Transactional
    public Response addItem(OrderItemDTO orderItemDTO) {
        Set<String> errorMessages = DTOValidator.validateDTO(orderItemDTO);

        if (!errorMessages.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        Optional<OrderItem> existingOrderItem = orderItemRepository.getOrderItemByProductId(orderItemDTO.getProductId());

        if (existingOrderItem.isPresent()) {
            System.out.println("Present !");
            orderItemRepository.delete(existingOrderItem.get().getId());
            OrderItem orderItem = createOrderItemFromDTO(orderItemDTO);
            orderItemRepository.create(orderItem);
            //inventoryRepository.update(orderItemDTO.getQuantity(), orderItemDTO.getProductId(), false);
            return Response.status(Response.Status.OK).entity(orderItem).build();
        } else {
            System.out.println("NOT Present !");
            OrderItem orderItem = createOrderItemFromDTO(orderItemDTO);
            orderItemRepository.create(orderItem);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.build(Long.toString(orderItem.getId()));

            return Response.created(builder.build()).entity(orderItem).build();
        }
    }

    private OrderItem createOrderItemFromDTO(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderItemDTO.getOrderId());
        orderItem.setProductId(orderItemDTO.getProductId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());
        return orderItem;
    }

    @Transactional
    public Response removeItem(Long orderItemId) {
        Optional<OrderItem> orderItem = orderItemRepository.getOrderItemById(orderItemId);

        if (orderItem.isPresent()) {
            orderItemRepository.delete(orderItem.get().getId());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Order item not found").build();
        }
    }

    @Transactional
    public Response getOrderItemProductByOrderId(Long orderId) {
        List<OrderItem> orderItemList = orderItemRepository.getOrderItemsByOrderId(orderId);

        if (!orderItemList.isEmpty()) {
            List<ProductDTO> resItemList = new ArrayList<>();
            orderItemList.forEach(item -> {
                resItemList.add(productService.getByProductId(item.getProductId()).readEntity(ProductDTO.class));
            });
            return Response.status(Response.Status.OK).entity(resItemList).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No order items found").build();
        }
    }

    @Transactional
    public Response getOrderItemByOrderId(Long orderId){
        List<OrderItem> orderItemList = orderItemRepository.getOrderItemsByOrderId(orderId);

        if(orderItemList.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(orderItemList).build();
    }

    @Transactional
    public Response getOrderItemsWithProductByOrderId(Long orderId) {
        List<OrderItemResponseDTO> orderItemResponseDTOList = orderItemRepository.getOrderItemsWithProduct(orderId);

        if(orderItemResponseDTOList.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(orderItemResponseDTOList).build();
    }
}
