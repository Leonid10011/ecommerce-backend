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
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class ProductImageService {

    @Inject
    private ProductImageRepository productImageRepository;

    public Response addProductImageURL(ProductImageDTO productImageDTO) {
        Set<String> violations = DTOValidator.validateDTO(productImageDTO);

        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(violations)
                    .build();
        }

        Optional<ProductImage> existingProductImage = productImageRepository.getProductImageByURL(productImageDTO.getImageURL());

        if (existingProductImage.isPresent()) {
            return Response.status(Response.Status.CONFLICT).entity("ProductImageUrl already exists.").build();
        }

        ProductImage productImage = createProductImageFromDTO(productImageDTO);

        productImageRepository.createProductImage(productImage);

        return Response.status(Response.Status.CREATED).entity(productImage).build();
    }

    public Response getById(Long id) {
        Optional<ProductImage> productImage = productImageRepository.findById(id);

        if (productImage.isPresent()) {
            ProductImageDTO productImageDTO = productImage.get().toDTO();
            return Response.status(Response.Status.OK).entity(productImageDTO).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Response getProductImageURLs() {
        List<ProductImage> productImageList = productImageRepository.getProductImages();

        return Response.ok(productImageList).build();
    }

    public Response delete(Long id) {
        Optional<ProductImage> productImage = productImageRepository.findById(id);
        if (productImage.isPresent()) {
            productImageRepository.deleteProductImage(id);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("ImageURL not found").build();
    }
    public Response handleProductImage(ProductDTO productDTO) {
        Optional<ProductImage> existingImage = productImageRepository.getProductImageByURL(productDTO.getImgURL());
        if (existingImage.isPresent()) {
            return Response.status(Response.Status.OK).build();
        } else {
            ProductImageDTO productImageDTO = createProductImageDTO(productDTO);
            return addProductImageURL(productImageDTO);
        }
    }
    // Helper functions
    public ProductImageDTO createProductImageDTO(ProductDTO productDTO) {
        return new ProductImageDTO(
                productDTO.getId(),
                productDTO.getImgURL()
        );
    }

    private ProductImage createProductImageFromDTO(ProductImageDTO productImageDTO) {
        ProductImage productImage = new ProductImage();
        productImage.setImageURL(productImageDTO.getImageURL());
        productImage.setProductID(productImageDTO.getProductID());
        return productImage;
    }

    public Optional<ProductImage> getProductImageByProduct(Long productId){
        return productImageRepository.getProductImageByProduct(productId);
    }
}