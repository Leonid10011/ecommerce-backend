package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.FavoriteProductDTO;
import com.lbcoding.ecommerce.model.FavoriteProduct;
import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.repository.FavoriteProductRepository;
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
public class FavoriteProductService {
    @Inject
    private FavoriteProductRepository favoriteProductRepository;

    @Inject
    UriInfo uriInfo;

    public Response createFavoriteProduct(FavoriteProductDTO favoriteProductDTO){
        Set<String> errorMessages = DTOValidator.validateDTO(favoriteProductDTO);
        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        Optional<FavoriteProduct> optionalFavoriteProduct = favoriteProductRepository.findByUserAndProduct(favoriteProductDTO.getUserId(), favoriteProductDTO.getProductId());

        if(optionalFavoriteProduct.isPresent()){
            return Response.status(Response.Status.CONFLICT).build();
        }

        FavoriteProduct favoriteProduct =  createFavoriteProductDTOtoFavoriteProduct(favoriteProductDTO);

        favoriteProductRepository.create(favoriteProduct);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(favoriteProduct.getId()));

        return Response.created(builder.build()).entity(favoriteProduct).build();
    }

    @Transactional
    public Response getByUserId(Long userId){
        List<Product> products = favoriteProductRepository.findByUserId(userId);

        if(products.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(products).build();
    }

    @Transactional
    public Response delete(Long id){
        Optional<FavoriteProduct> optionalFavoriteProduct = favoriteProductRepository.findById(id);

        if(optionalFavoriteProduct.isPresent()){
            favoriteProductRepository.delete(id);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    private FavoriteProduct createFavoriteProductDTOtoFavoriteProduct(FavoriteProductDTO favoriteProductDTO){
        return new FavoriteProduct(
                favoriteProductDTO.getUserId(),
                favoriteProductDTO.getProductId()
        );
    }

    @Transactional
    public Response deleteByUserAndProduct(Long userId, Long productId){
        Optional<FavoriteProduct> optionalFavoriteProduct = favoriteProductRepository.findByUserAndProduct(userId, productId);

        if(optionalFavoriteProduct.isPresent()){
            favoriteProductRepository.deleteByUserAndProduct(userId, productId);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
