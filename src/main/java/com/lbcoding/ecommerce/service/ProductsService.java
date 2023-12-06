package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.request.ProductsRequestDTO;
import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ProductsService {
    private static final Logger logger = LoggerFactory.getLogger(ProductsService.class);
    @Inject
    ProductsRepository productsRepository;
    @Inject
    ImagesRepository imagesRepository;
    @Inject
    CategoryRepository categoryRepository;
    @Inject
    SizesRepository sizesRepository;
    @Inject
    InventoryRepository inventoryRepository;
    /**
     *
     * @param productsRequestDTO
     * @return
     */
    @Transactional
    public Response create(ProductsRequestDTO productsRequestDTO){
        try {
            // creating the product entity
            Product product = new Product();
            product.setName(productsRequestDTO.getName());
            product.setDescription(productsRequestDTO.getDescription());
            product.setPrice(productsRequestDTO.getPrice());
            productsRepository.create(product);
            // creating image entities
            imagesRepository.setImagesForProduct(product, productsRequestDTO.getImageUrl());
            // creating category entities
            categoryRepository.setCategoryForProduct(product, productsRequestDTO.getCategory());
            // set sizes for product
            sizesRepository.setSizesForProduct(product, productsRequestDTO.getSizes());
            // set inventory for product
            inventoryRepository.setInventoryForProduct(product, 100);
            return Response.status(Response.Status.CREATED).build();

        } catch( Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Winner").build();
        }
    }
}
