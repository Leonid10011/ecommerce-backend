package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.dto.request.ProductsRequestDTO;
import com.lbcoding.ecommerce.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductsRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProductsRepository.class);
    @Inject
    EntityManager entityManager;
    @Transactional
    public void create(Products product) {
        logger.info("Persisting product");
        if(product == null){
            throw new IllegalArgumentException("Product cannot be null");
        }
        entityManager.persist(product);
        logger.info("Product persisted successfully");

    }
    public List<Products> findAll(){
        logger.info("Querying to find all products");
        TypedQuery<Products> query = entityManager.createQuery(
                "SELECT p FROM Products p", Products.class
        );

        List<Products> products = query.getResultList();
        if(products.isEmpty()){
            logger.info("Products is empty");
            return products;
        }
        logger.info("Products found successfully");
        return products;
    }
    /**
     * Attempts to find a product by ID.
     * @param id Unique identifier of the product
     * @return Product on success, otherwise empty Optional
     */
    public Optional<Products> findById(Long id){
        logger.info("Querying find product with ID: " + id);
        Products product = entityManager.find(Products.class, id);
        if (product != null){
            logger.info("Found product with ID: " + id);
            return Optional.of(product);
        }
        logger.warn("Product not found with ID: " + id);
        return Optional.empty();
    }

    /**
     * Attempts to delete a product by ID.
     * @param id Unique identifier of the product
     */
    public void delete(Long id){
        logger.info("Deleting product with ID: " + id);
        findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        logger.info("Product deleted successfully with ID: " + id);
    }

    /**
     * Attempts to create a product with additional attributes
     * @param productDTO
     */
    @Transactional
    public void insert(ProductsRequestDTO productDTO){
        logger.info("Persisting product");
        Products product = new Products();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        entityManager.persist(product);
        logger.info("Persisted Product successfully with ID: " + product.getId());
        // insert product images into Images table
        logger.info("Persisting images for product");
        Arrays.stream(productDTO.getImageUrl()).forEach(
        imageUrl -> {
                    Images newImage = new Images();
                    newImage.setImageUrl(imageUrl);
                    newImage.setProduct(product);
                    entityManager.persist(newImage);
                }
        );
        logger.info("Images persisted successfully.");
        // find category ID and insert
        logger.info("Retrieving and setting category for product with ID: " + product.getId());
        TypedQuery<Categories>  query = entityManager.createQuery(
                "SELECT c FROM Categories c WHERE c.name = :categoryName", Categories.class
        ).setParameter("categoryName", productDTO.getCategory());
        try{
            Categories category = query.getSingleResult();
            product.setCategory(category);
            logger.info("Category retrieved and set successfully for product with ID: " + product.getId());
        } catch ( NoResultException e) {
            logger.info("Category " + productDTO.getCategory() + " not found. Please provide a valid category");
            throw new NotFoundException("Category " + productDTO.getCategory() + " not found. Please provide a valid category");
        }
        logger.info("Setting sizes and inventory");
        // insert into sizes junction table
        Arrays.stream(productDTO.getSizes()).forEach(size -> {
            Sizes newSize = findSizeByDescription(size);
            product.getSizes().add(newSize);
            newSize.getProducts().add(product);

            Inventory inventory = new Inventory();
            inventory.setProductId(product.getId());
            inventory.setSizeId(newSize.getId());
            inventory.setQuantity(100);
            entityManager.persist(inventory);
        });
        logger.info("Set  sizes and inventory successfully");
    }

    public Sizes findSizeByDescription(String description){
        TypedQuery<Sizes> query = entityManager.createQuery(
                "SELECT s FROM Sizes s WHERE s.description = :description", Sizes.class
        ).setParameter("description", description);

        try {
            return query.getSingleResult();
        } catch ( NoResultException e) {
            throw new NotFoundException("Size " + description + " not found. Please provide valid sizes.");
        }
    }
}
