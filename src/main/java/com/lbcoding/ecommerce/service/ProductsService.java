package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.request.ProductsRequestDTO;
import com.lbcoding.ecommerce.dto.response.ProductsResponseDTO;
import com.lbcoding.ecommerce.model.Category;
import com.lbcoding.ecommerce.model.Image;
import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.model.Size;
import com.lbcoding.ecommerce.repository.*;
import com.lbcoding.ecommerce.service.inerfaces.IProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NonUniqueResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductsService implements IProductService {
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
     * Attempts to create a prodcut with all necessary information. The DTO contains all data that is needed to store in each table, like category, image, size and inventory.
     *
     * @param productsRequestDTO A DTO specific for request to insert all needed information into the respective tables.
     * @return Success:
     */
    @Transactional
    public Response create(ProductsRequestDTO productsRequestDTO) {
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

            return Response.status(Response.Status.CREATED).entity("Success inserting product").build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to insert product.").build();
        }
    }

    /**
     * Attempts to find a product
     *
     * @param name the name of the product
     * @return Success: A DTO containing all information about the product with status 200
     * Failure: An errorMessage with status code 404
     */
    public Response getByName(String name) {
        logger.info("Received request to retrieve product by NAME: " + name);
        try {
            Optional<Product> product = productsRepository.findByName(name);
            if (product.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Product not found with NAME: " + name).build();
            }
            ProductsResponseDTO productsResponseDTO = entityToResponseDTO(product.get());
            return Response.status(Response.Status.OK).entity(productsResponseDTO).build();
        } catch( NonUniqueResultException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Duplicate Products with that name exist. Aborting retrieval...").build();
        }
    }

    /**
     * Attempts to find a product by id
     *
     * @param id the id of the product
     * @return Success: A DTO containing all information about the product with status 200
     * Failure: An errorMessage with status code 404
     */
    public Response getById(long id) {
        logger.info("Received request to retrieve product by ID: " + id);
        Optional<Product> product = productsRepository.findById(id);
        if (product.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found with ID: " + id).build();
        }
        ProductsResponseDTO productsResponseDTO = entityToResponseDTO(product.get());
        return Response.status(Response.Status.OK).entity(productsResponseDTO).build();
    }

    /**
     * @param page the number of the pageSize block that is selected
     * @param pageSize the number of entities in a page block
     * @return
     */
    public Response getAll(int page, int pageSize) {
        logger.info("Attempts to find all products");
        List<Product> productList = productsRepository.findAll(page, pageSize);
        List<ProductsResponseDTO> productsResponseDTOs = productList.stream().map(this::entityToResponseDTO).toList();
        return Response.status(Response.Status.OK).entity(productsResponseDTOs).build();
    }
    /**
     * Attempts to delete a product by ID
     * @param id the ID of the product to delete
     * @return NoContent with status code 204
     */
    public Response delete(long id){
        logger.info("Received request to delete product with ID: " + id);
        productsRepository.delete(id);
        logger.info("Successfully deleted product with ID: " + id);
        return Response.noContent().build();
    }

    private ProductsResponseDTO entityToResponseDTO(Product product) {
        String[] sizes = product.getSizes().stream().map(Size::getName).toList().toArray(new String[0]);
        String[] images = product.getImages().stream().map(Image::getImageUrl).toList().toArray(new String[0]);
        String[] categories = product.getCategories().stream().map(Category::getName).toList().toArray(new String[0]);
        return new ProductsResponseDTO(
                product.getProduct_id(),
                product.getName(),
                product.getPrice(),
                sizes,
                images,
                0,
                categories
        );
    }
}
