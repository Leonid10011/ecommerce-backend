package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.*;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.model.ProductImage;
import com.lbcoding.ecommerce.repository.InventoryRepository;
import com.lbcoding.ecommerce.repository.ProductImageRepository;
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
    private InventoryRepository inventoryRepository;

    @Inject
    private ProductImageService productImageService;

    @Inject
    private ProductImageRepository productImageRepository;

    @Inject
    private UriInfo uriInfo;

    /**
     * Retrieves all available products and send them to the client.
     *
     * @return A {@link Response} object containing either a List of products if exists, otherwise a NOT FOUND status code is returned.
     */
    public Response getProducts() {
        List<Product> products = productRepository.getProducts();

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
     *
     * @param productId
     * @return
     */
    public Response getByProductId(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if(productOptional.isPresent()){
            ProductDTO productDTO = mapProductToProductDTO(productOptional.get());
            return Response.status(Response.Status.OK).entity(productDTO).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Product not found or inventory not available.").build();
    }

    /**
     *
     * @param product
     * @return
     */
    private ProductDTO mapProductToProductDTO(Product product) {
        Inventory inventory = inventoryRepository.findByProductId(product.getId());
        Optional<ProductImage> productImage = productImageRepository.getProductImageByProduct(product.getId());

        int quantity = (inventory != null) ? inventory.getQuantity() : 0;
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
     *
     * @param id
     * @return
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
     *
     * @param productDTO
     * @return
     */
    private Response validateProductDTO(ProductDTO productDTO) {
        Set<String> validationErrors = DTOValidator.validateDTO(productDTO);
        if (!validationErrors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors).build();
        }
        return null; // Indicates no validation errors
    }

    /**
     *
     * @param productName
     * @return
     */
    private Response checkExistingProduct(String productName) {
        Optional<Product> existingProduct = productRepository.findProductByName(productName);
        if (existingProduct.isPresent()) {
            return Response.status(Response.Status.CONFLICT).entity("Product with this name already exists.").build();
        }
        return null; // Indicates the product does not exist
    }
    /**
     * Handles the inventory for a newly created or updated product. It checks if an inventory
     * record already exists for the given product. If it does, it updates the inventory quantity;
     * otherwise, it creates a new inventory record with the specified quantity.
     *
     * @param productDTO The data transfer object containing product details including quantity.
     * @param productId The ID of the product for which inventory is being handled.
     * @return A {@link Response} object indicating the success or failure of the operation.
     * If successful, the response status is OK; if there's an issue with the quantity or
     * if the inventory can't be updated or created, a BAD REQUEST status is returned.
     */
    private Response handleInventory(ProductDTO productDTO, Long productId) {
        Inventory existingInventory = inventoryRepository.findByProductId(productId);
        Response quantityResponse;

        if (existingInventory != null) {
            quantityResponse = inventoryService.updateQuantity(productDTO.getQuantity(), existingInventory.getId(), true);
        } else {
            InventoryDTO inventoryDTO = inventoryService.createInventoryDTO(productDTO);
            quantityResponse = inventoryService.setQuantity(inventoryDTO);
        }

        return quantityResponse;
    }
    private Response handleProductImage(ProductDTO productDTO) {
        Optional<ProductImage> existingImage = productImageRepository.getProductImageByURL(productDTO.getImgURL());
        if (existingImage.isPresent()) {
            return Response.status(Response.Status.OK).build();
        } else {
            ProductImageDTO productImageDTO = productImageService.createProductImageDTO(productDTO);
            return productImageService.addProductImageURL(productImageDTO);
        }
    }
    @Transactional
    public Response createProduct(ProductDTO productDTO) {
        // Validate ProductDTO
        Response validationResponse = validateProductDTO(productDTO);
        if (validationResponse != null) return validationResponse;

        // Check if product exists
        Response existingProductResponse = checkExistingProduct(productDTO.getName());
        if (existingProductResponse != null) return existingProductResponse;

        // Create and save new product
        Product product = createProductModel(productDTO);
        productRepository.createProduct(product);
        productDTO.setId(product.getId());

        // Handle inventory and image
        Response inventoryResponse = handleInventory(productDTO, product.getId());
        Response imageResponse = handleProductImage(productDTO);

        // Check for errors in inventory or image handling
        if (inventoryResponse.getStatus() != Response.Status.OK.getStatusCode() ||
                imageResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("There was a problem with quantity or imageURL").build();
        }

        // Build response
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(product.getId()));
        return Response.created(builder.build()).entity(productDTO).build();
    }

    /*
    // Create a product
    @Transactional
    public Response createProduct(ProductDTO productDTO) {
        Set<String> validationErrors = DTOValidator.validateDTO(productDTO);

        if (!validationErrors.isEmpty()) {
            System.out.println("Errors ");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(validationErrors)
                    .build();
        }

        Optional<Product> existingProduct = productRepository.findProductByName(productDTO.getName());

        if (existingProduct.isPresent()) {
            return Response.status(Response.Status.CONFLICT).entity("Product with this name already exists.").build();
        }

        Product product = createProductModel(productDTO);
        productRepository.createProduct(product);

        productDTO.setId(product.getId());

        Inventory existingInventory = inventoryRepository.findByProductId(product.getId());

        Response quantityResponse;
        Response imageResponse;

        if (existingInventory != null) {
            quantityResponse = inventoryService.updateQuantity(productDTO.getQuantity(), existingInventory.getId(), true);
        } else {
            InventoryDTO inventoryDTO = inventoryService.createInventoryDTO(productDTO);
            quantityResponse = inventoryService.setQuantity(inventoryDTO);
        }

        Optional<ProductImage> existingImage = productImageRepository.getProductImageByURL(productDTO.getImgURL());

        if (existingImage.isPresent()) {
            imageResponse = Response.status(Response.Status.OK).build();
        } else {
            ProductImageDTO productImageDTO = productImageService.createProductImageDTO(productDTO);
            imageResponse = productImageService.addProductImageURL(productImageDTO);
        }

        if (quantityResponse.getStatus() == 400 || quantityResponse.getStatus() == 409 || imageResponse.getStatus() == 409) {
            System.out.println("Error " + quantityResponse.getStatus());
            return Response.status(Response.Status.BAD_REQUEST).entity("There was a problem with quantity or imageURL").build();
        }

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.build(Long.toString(product.getId()));

        productDTO.setId(product.getId());

        return Response.created(builder.build()).entity(productDTO).build();
    }
    */
    // Search for products by name
    public Response getSearchName(String searchTerm) {
        List<Product> products = productRepository.searchProductsByName(searchTerm);
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

    // Create a ProductDTO from a Product and Inventory
    private ProductDTO createProductDTO(Product product, Inventory inventory) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategoryID(),
                inventory.getQuantity(),
                "Not yet"
        );
    }

    // Create a Product from a ProductDTO
    private Product createProductModel(ProductDTO productDTO) {
        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getCategoryID()
        );
    }
}

