package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.*;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.model.ProductImage;
import com.lbcoding.ecommerce.repository.ProductRepository;
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
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductService {
    @Inject
    private ProductRepository productRepository;
    @Inject
    private InventoryService inventoryService;
    @Inject
    private ProductImageService productImageService;
    @Inject
    private UriInfo uriInfo;
    /**
     * Retrieves all available products and send them to the client.
     *
     * @return A Response object containing either a List of products if exists, otherwise a NOT FOUND status code is returned.
     */
    public Response getProducts() {
        List<Product> products = productRepository.findAll();

        if (!products.isEmpty()) {
            // Map products to ProductDTO
            List<ProductDTO> productDTOList = products.stream()
                    .map(this::mapProductToProductDTO)
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(productDTOList).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
    /**
     * Retrieves a specific product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return A Response object with the ProductDTO if found; otherwise, a NOT FOUND status code.
     */
    public Response getByProductId(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            ProductDTO productDTO = mapProductToProductDTO(productOptional.get());
            return Response.status(Response.Status.OK).entity(productDTO).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Product not found or inventory not available.").build();
    }
    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return A no content Response if deletion is successful; otherwise, a NOT FOUND status code.
     */
    @Transactional
    public Response deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            inventoryService.delete(product.get().getId());
            productImageService.delete(product.get().getId());
            productRepository.deleteProduct(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found.").build();
        }
    }
    /**
     * Creates a new product.
     *
     * @param productDTO The ProductDTO containing the product data.
     * @return A created Response with the ProductDTO if successful; otherwise, a BAD REQUEST or CONFLICT response.
     */
    @Transactional
    public Response createProduct(ProductDTO productDTO) {
        // Validate ProductDTO
        Response validationResponse = validateProductDTO(productDTO);
        if (validationResponse != null) return validationResponse;
        // Check if product exists
        Response existingProductResponse = checkProductExists(productDTO.getName());
        if (existingProductResponse != null) return existingProductResponse;
        // Create and save new product
        Product product = mapDtoToEntity(productDTO);
        productRepository.create(product);
        productDTO.setId(product.getId());
        // Handle inventory
        Response inventoryResponse = inventoryService.handleInventory(productDTO, product.getId());
        if(inventoryResponse.getStatus() != Response.Status.OK.getStatusCode())
            return Response.status(Response.Status.BAD_REQUEST).entity("There was a problem with quantity or imageURL").build();
        // handle product image
        Response imageResponse = productImageService.handleProductImage(productDTO);
        if(imageResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("There was a problem with quantity or imageURL").build();
        }
        // Build response
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(product.getId()));
        return Response.created(builder.build()).entity(productDTO).build();
    }
    /**
     * Searches for products by a given name.
     *
     * @param searchTerm The term to search for in product names.
     * @return A Response with a list of ProductDTOs if matches are found; otherwise, a NOT FOUND status code.
     */
    public Response searchProductsByName(String searchTerm) {
        List<Product> products = productRepository.searchByName(searchTerm);
        if (!products.isEmpty()) {
            // Map products to ProductDTO
            List<ProductDTO> productDTOList = products.stream()
                    .map(this::mapProductToProductDTO)
                    .collect(Collectors.toList());
            return Response.status(Response.Status.OK).entity(productDTOList).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    /**
     * Checks if a product with the given name already exists.
     *
     * @param productName The name of the product to check.
     * @return A CONFLICT Response if the product exists; otherwise, null.
     */
    private Response checkProductExists(String productName) {
        Optional<Product> existingProduct = productRepository.findByName(productName);
        if (existingProduct.isPresent()) {
            return Response.status(Response.Status.CONFLICT).entity("Product with this name already exists.").build();
        }
        return null; // Indicates the product does not exist
    }
    /**
     * Validates the given ProductDTO.
     *
     * @param productDTO The ProductDTO to validate.
     * @return A BAD REQUEST Response with validation errors if any; otherwise, null.
     */
    private Response validateProductDTO(ProductDTO productDTO) {
        Set<String> validationErrors = DTOValidator.validateDTO(productDTO);
        if (!validationErrors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors).build();
        }
        return null; // Indicates no validation errors
    }
    /**
     * Maps a Product entity to a ProductDTO.
     *
     * @param product The Product entity to be mapped.
     * @return The mapped ProductDTO.
     */
    private ProductDTO mapProductToProductDTO(Product product) {
        Optional<Inventory> inventory = inventoryService.getInventoryByProductId(product.getId());
        Optional<ProductImage> productImage = productImageService.getProductImageByProduct(product.getId());

        int quantity = inventory.map(Inventory::getQuantity).orElse(0);
        String imageURL = (productImage.isPresent()) ? productImage.get().getImageURL() : "Not yet";

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategoryID(),
                quantity,
                imageURL
        );
    }
    /**
     * Maps a ProductDTO to a Product entity.
     *
     * @param productDTO The ProductDTO to map.
     * @return The mapped Product entity.
     */
    private Product mapDtoToEntity(ProductDTO productDTO) {
        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getCategoryID()
        );
    }
}

