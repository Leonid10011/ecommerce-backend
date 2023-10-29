package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.CartItemDTO;
import com.lbcoding.ecommerce.model.CartItem;
import com.lbcoding.ecommerce.repository.CartItemRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class CartItemService {
    @Inject
    private CartItemRepository cartItemRepository;

    @Inject
    UriInfo uriInfo;

    @Transactional
    public Response createCartItem(CartItemDTO cartItemDTO){
        Set<String> errorMessages = DTOValidator.validateDTO(cartItemDTO);
        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        Optional<CartItem> cartItemOptional = cartItemRepository.findByUserAndProduct(cartItemDTO.getUserId(), cartItemDTO.getProductId());

        if(cartItemOptional.isPresent()){
            cartItemRepository.update(cartItemOptional.get().getId(), cartItemDTO.getQuantity());
            return Response.status(Response.Status.OK).entity(cartItemOptional).build();
        } else {
            CartItem cartItem = cartItemDTOtoCartItem(cartItemDTO);
            cartItemRepository.create(cartItem);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.build(Long.toString(cartItem.getId()));

            return Response.created(builder.build()).entity(cartItem).build();
        }
    }

    public Response getCartItemById(Long id){
        Optional<CartItem> cartItem = cartItemRepository.findById(id);

        if(cartItem.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
        }

        return Response.status(Response.Status.OK).entity(cartItem).build();
    }

    public Response getCartItemsByUser(Long userId){
        List<CartItem> cartItem = cartItemRepository.getByUser(userId);

        if(cartItem.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(cartItem).build();
    }

    public Response deleteCartItem(Long id){
        Optional<CartItem> cartItem = cartItemRepository.findById(id);

        if(cartItem.isPresent()){
            cartItemRepository.delete(id);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
    private CartItem cartItemDTOtoCartItem(CartItemDTO cartItemDTO){
        return new CartItem(
            cartItemDTO.getUserId(),
            cartItemDTO.getProductId(),
            cartItemDTO.getQuantity()
        );
    }
}
