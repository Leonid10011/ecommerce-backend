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
     */
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
        Product product = productRepository.getById(productId);

        if(product == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("Not Found.").build();

        Inventory inventory = inventoryRepository.get(product.getId());

        ProductDTO productDTO = new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategoryID(),
                inventory.getQuantity(),
                "Not yet"
        );

        return Response.status(Response.Status.OK).entity(productDTO).build();
    }

    /**
     * Delete a prodcut by id
     * @param id
     * @return Confirmation Text : String
     * @todo Need to also delete its Inventory entry and ImageURL
     */
    public Response deleteProduct(Long id){
        Product product = productRepository.getById(id);

        if(product != null){
            inventoryService.delete(product.getId());
            productImageService.delete(product.getId());
            productRepository.deleteProduct(id);

            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Produkt nicht gefunden.").build();
        }
    }

    /**
     * create a product together with a quantity
     * @return Product Entity
     */
    @Transactional
    public Response createProduct(ProductDTO productDTO){
        Set<String> validationErrors = DTOValidator.validateDTO(productDTO);

        if(!validationErrors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(validationErrors)
                    .build();
        }

        Product existingProduct = productRepository.getProductByName(productDTO.getName());

        if(existingProduct != null){
            return Response.status(Response.Status.CONFLICT).entity("Product with this name already exists.").build();
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategoryID(productDTO.getCategoryID());

        productRepository.createProduct(product);

        Inventory existingInventory = inventoryRepository.get(product.getId());

        Response quantityResponse;
        Response imageResponse;
        if(existingInventory != null){
            // An inventory already exists for this product, so we need to update the quantity
            quantityResponse = inventoryService.updateQuantity(productDTO.getQuantity(), existingInventory.getId(), true);
        } else {
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setProductID(productDTO.getId());
            inventoryDTO.setQuantity(productDTO.getQuantity());

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
