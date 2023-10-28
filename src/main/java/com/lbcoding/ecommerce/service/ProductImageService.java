package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.dto.ProductImageDTO;
import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.model.ProductImage;
import com.lbcoding.ecommerce.repository.ProductImageRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class ProductImageService {

    DTOValidator validator = new DTOValidator();

    @Inject
    ProductImageRepository productImageRepository;

    public Response addProductImageURL(ProductImageDTO productImageDTO){
        Set<String> violations = DTOValidator.validateDTO(productImageDTO);

        if(!violations.isEmpty()){
            Response.status(Response.Status.BAD_REQUEST)
                    .entity(violations)
                    .build();
        }

        ProductImage existingProductImage = productImageRepository.getProductImageByURL(productImageDTO.getImageURL());

        if(existingProductImage != null){
            return Response.status(Response.Status.CONFLICT).entity("ProductImageUrl already exists.").build();
        }

        ProductImage productImage = new ProductImage();
        productImage.setImageURL(productImageDTO.getImageURL());
        productImage.setProductID(productImageDTO.getProductID());

        productImageRepository.createProductImage(productImage);

        return  Response.ok(Response.Status.CREATED).entity(productImage).build();
    }

    public Response get(Long id){
        ProductImage productImage = productImageRepository.get(id);

        if(productImage != null){
            ProductImageDTO productImageDTO = productImage.toDTO();

            return Response.status(Response.Status.OK).entity(productImageDTO).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Response getProductImageURLs(){
        List<ProductImage> productImageList = productImageRepository.getProductImages();

        return Response.ok(productImageList).build();
    }

    public Response delete(Long id){
        ProductImage productImage = productImageRepository.get(id);
        if(productImage != null){
            productImageRepository.deleteProductImage(id);

            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("ImageURL not found").build();
    }

    public ProductImageDTO createProductImageDTO(ProductDTO productDTO){
        return new ProductImageDTO(
                productDTO.getId(),
                productDTO.getImgURL()
        );
    }
}
