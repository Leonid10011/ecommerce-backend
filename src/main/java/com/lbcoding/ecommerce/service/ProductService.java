/*
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
    ProductRepository productRepository;

    @Inject
    InventoryService inventoryService;

    @Inject
    InventoryRepository inventoryRepository;
    @Inject
    ProductImageService productImageService;

    @Inject
    ProductImageRepository productImageRepository;

    @Inject
    UriInfo uriInfo;

    /**
     * Retrieve a List of all products
     * @return List<Product>
     * @comment We set the quantity to 0 if no entry exists and imageURL to "" if no productImage exists

    public Response getProducts(){
        List<Product> products = productRepository.getProducts();

        if(!products.isEmpty()){
            List<ProductDTO> productDTOList = products.stream()
                    .map(product -> product.toDTO(
                            inventoryRepository.get(product.getId()) == null ? 0 : inventoryRepository.get(product.getId()).getQuantity(),
                            productImageRepository.get(product.getId()) == null ? "" : productImageRepository.get(product.getId()).getImageURL())
                    )
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(productDTOList).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    public Response get(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            Inventory inventory = inventoryRepository.get(product.getId());

            if (inventory != null) {
                ProductDTO productDTO = createProductDTO(product, inventory);
                return Response.status(Response.Status.OK).entity(productDTO).build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Product not found or inventory not available.").build();
    }

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

    private Product createProductModel(ProductDTO productDTO) {
        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getCategoryID()
        );
    }

     /**
     * Löscht ein Produkt anhand seiner ID.
     *
     * @param id Die ID des zu löschenden Produkts.
     * @return Eine Bestätigungsnachricht als Zeichenkette (String).

    public Response deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            // Lösche das zugehörige Inventar und die Bild-URL des Produkts.
            inventoryService.delete(product.get().getId());
            productImageService.delete(product.get().getId());

            // Lösche das Produkt aus dem Repository.
            productRepository.deleteProduct(id);

            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Produkt nicht gefunden.").build();
        }
    }

    /**
     * create a product together with a quantity
     * @return Product Entity

    @Transactional
    public Response createProduct(ProductDTO productDTO){
        Set<String> validationErrors = DTOValidator.validateDTO(productDTO);

        if(!validationErrors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(validationErrors)
                    .build();
        }

        Optional<Product> existingProduct = productRepository.findProductByName(productDTO.getName());

        if(existingProduct.isPresent()){
            return Response.status(Response.Status.CONFLICT).entity("Product with this name already exists.").build();
        }

        Product product = createProductModel(productDTO);

        productRepository.createProduct(product);

        Inventory existingInventory = inventoryRepository.get(product.getId());

        Response quantityResponse;
        Response imageResponse;

        if(existingInventory != null){
            // An inventory already exists for this product, so we need to update the quantity
            quantityResponse = inventoryService.updateQuantity(productDTO.getQuantity(), existingInventory.getId(), true);
        } else {
            InventoryDTO inventoryDTO = inventoryService.createInventoryDTO(productDTO);

            quantityResponse = inventoryService.setQuantity(inventoryDTO);
        }

        ProductImage existingImage = productImageRepository.getProductImageByURL(productDTO.getImgURL());

        if(existingImage != null) {
            imageResponse = Response.status(Response.Status.OK).build();
        } else {
            ProductImageDTO productImageDTO = new ProductImageDTO();
            productImageDTO.setProductID(productDTO.getId());
            productImageDTO.setImageURL(productDTO.getImgURL());
            System.out.println("IMAGE, "+ productDTO.getImgURL());
            imageResponse = productImageService.addProductImageURL(productImageDTO);
        }

        if(quantityResponse.getStatus() == (400) || quantityResponse.getStatus() == 409 || imageResponse.getStatus() == 409){
            return Response.status(Response.Status.BAD_REQUEST).entity("There was a Problem with quantity or imageURL").build();
        }

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.build(Long.toString(product.getId()));

        ProductDTO productDTOResponse = productDTO;
        productDTOResponse.setId(product.getId());

        return Response.created(builder.build()).entity(productDTOResponse).build();
    }

    public Response getSearchName(String searchTerm){
        List<Product> products = productRepository.getSearchName(searchTerm);
        if(products != null){
            List<ProductDTO> productDTOList = products.stream()
                    .map(product -> {
                        Inventory inventory = inventoryRepository.get(product.getId());
                        ProductImage productImage = productImageRepository.getProductImagebyProduct(product.getId());

                        return product.toDTO(inventory != null ? inventory.getQuantity() : 0, productImage != null ? productImage.getImageURL() : "test");
                    })
                    .collect(Collectors.toList());
            return Response.status(Response.Status.OK).entity(productDTOList).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
*/
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

    // Get a list of all products
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

    // Get a product by its ID
    public Response getByProductId(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Inventory inventory = inventoryRepository.findByProductId(product.getId());

            if (inventory != null) {
                ProductDTO productDTO = createProductDTO(product, inventory);
                return Response.status(Response.Status.OK).entity(productDTO).build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Product not found or inventory not available.").build();
    }

    // Map a Product to ProductDTO
    private ProductDTO mapProductToProductDTO(Product product) {
        Inventory inventory = inventoryRepository.findByProductId(product.getId());
        ProductImage productImage = productImageRepository.getProductImagebyProduct(product.getId());

        int quantity = (inventory != null) ? inventory.getQuantity() : 0;
        String imageURL = (productImage != null) ? productImage.getImageURL() : "Not yet";

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

    // Delete a product by its ID
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

        Inventory existingInventory = inventoryRepository.findByProductId(product.getId());

        Response quantityResponse;
        Response imageResponse;
        System.out.println("Here ");

        if (existingInventory != null) {
            quantityResponse = inventoryService.updateQuantity(productDTO.getQuantity(), existingInventory.getId(), true);
        } else {
            InventoryDTO inventoryDTO = inventoryService.createInventoryDTO(productDTO);
            quantityResponse = inventoryService.setQuantity(inventoryDTO);
        }

        ProductImage existingImage = productImageRepository.getProductImageByURL(productDTO.getImgURL());

        if (existingImage != null) {
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

        ProductDTO productDTOResponse = productDTO;
        productDTOResponse.setId(product.getId());

        return Response.created(builder.build()).entity(productDTOResponse).build();
    }

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

