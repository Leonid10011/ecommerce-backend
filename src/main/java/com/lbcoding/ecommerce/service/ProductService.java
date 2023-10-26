package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.*;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.model.ProductImage;
import com.lbcoding.ecommerce.model.ProductWithQuantity;
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
                    .map(product -> product.toImageDTO(
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

        ProductWithURLDTO productWithURLDTO = new ProductWithURLDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategoryID(),
                inventory.getQuantity(),
                "Not yet"
        );

        return Response.status(Response.Status.OK).entity(productWithURLDTO).build();
    }

    /**
     * Create a Product
     * @param productDTO
     * @return Product Entity
     */
    public Response createProduct(ProductDTO productDTO){

        Set<String> validationErrors = DTOValidator.validateDTO(productDTO);

        if(!validationErrors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(validationErrors)
                    .build();
        }

        Product existingProduct = productRepository.getProductByName(productDTO.getName());

        if(existingProduct != null){
            return Response.status(Response.Status.CONFLICT).entity("Product already exists").build();
        }

        Product product = new Product();

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategoryID(productDTO.getCategoryID());
        product.setPrice(productDTO.getPrice());

        productRepository.createProduct(product);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(product.getId()));

        return Response.created(builder.build()).entity(product).build();
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
     * @param productWithQuantityDTO
     * @return Product Entity
     */
    @Transactional
    public Response createProduct(ProductWithQuantityDTO productWithQuantityDTO){
        // We create a newProduct to extract Product related Properties
        ProductDTO newProductDTO = new ProductDTO();
        // Set the attribute vor previous overload
        newProductDTO.setName(productWithQuantityDTO.getName());
        newProductDTO.setDescription(productWithQuantityDTO.getDescription());
        newProductDTO.setPrice(productWithQuantityDTO.getPrice());
        newProductDTO.setCategoryID(productWithQuantityDTO.getCategoryID());

        Response productCreationResponse = createProduct(newProductDTO);

        if(productCreationResponse.getStatus() == 201){
            // If object can be created set quantity in Inventory
            InventoryDTO newInventoryDTO = new InventoryDTO();
            newInventoryDTO.setQuantity(productWithQuantityDTO.getQuantity());

            if(productCreationResponse.getEntity() instanceof Product){
                // Create the inventory entry with retrieved corresponding product id
                Product createdProduct = (Product) productCreationResponse.getEntity();
                newInventoryDTO.setProductID(createdProduct.getId());
                // Create a response object with quantity so the next overload can use it
                inventoryService.setQuantity(newInventoryDTO);

                UriBuilder builder = uriInfo.getAbsolutePathBuilder();
                builder.path(Long.toString(((Product) productCreationResponse.getEntity()).getId()));

                return Response.created(builder.build()).entity(productCreationResponse.getEntity()).build();
            }
        }
        return Response.status(Response.Status.CONFLICT).entity("Product with this quantity already exists").build();
    }

    /**
     * We extend the product with quantity and imageURL
     * @param productWithURLDTO
     * @return Product Entity
     */
    public Response createProduct(ProductWithURLDTO productWithURLDTO){
        ProductWithQuantityDTO productWithQuantityDTO = new ProductWithQuantityDTO();
        // Set the attributes for previous overload
        productWithQuantityDTO.setName(productWithURLDTO.getName());
        productWithQuantityDTO.setDescription(productWithURLDTO.getDescription());
        productWithQuantityDTO.setPrice(productWithURLDTO.getPrice());
        productWithQuantityDTO.setQuantity(productWithURLDTO.getQuantity());
        productWithQuantityDTO.setCategoryID(productWithURLDTO.getCategoryID());

        Response productWithQuantityCreation = createProduct(productWithQuantityDTO);

        if(productWithQuantityCreation.getStatus() == 201){
            ProductImageDTO productImageDTO = new ProductImageDTO();
            productImageDTO.setImageURL(productWithURLDTO.getImageURL());
            if(productWithQuantityCreation.getEntity() instanceof Product){
                Product createdProduct = (Product) productWithQuantityCreation.getEntity();
                productImageDTO.setProductID(createdProduct.getId());
                productImageService.addProductImageURL(productImageDTO);

                UriBuilder builder = uriInfo.getAbsolutePathBuilder();
                builder.path(Long.toString(((Product) productWithQuantityCreation.getEntity()).getId()));

                return Response.created(builder.build()).entity(productWithQuantityCreation.getEntity()).build();
            }
        }

        return Response.status(Response.Status.CONFLICT).entity("Product with this quantity already exists").build();
    }

    public Response getSearchName(String searchTerm){
        List<Product> products = productRepository.getSearchName(searchTerm);
        if(products != null){
            List<ProductWithQuantityDTO> productDTOList = products.stream()
                    .map(product -> {
                        Inventory inventory = inventoryRepository.get(product.getId());
                        ProductImage productImage = productImageRepository.getProductImagebyProduct(product.getId());

                        return product.toImageDTO(inventory != null ? inventory.getQuantity() : 0, productImage != null ? productImage.getImageURL() : "test");
                    })
                    .collect(Collectors.toList());
            return Response.status(Response.Status.OK).entity(productDTOList).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
