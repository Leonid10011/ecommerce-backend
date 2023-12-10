package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.repository.interfaces.IProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductsRepository implements IProductRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProductsRepository.class);
    @Inject
    EntityManager entityManager;

    /**
     * Persists a product
     * @param product A Product entity
     * @return newProduct a Product entity
     * @throws IllegalArgumentException when null was provided as argument
     * @throws EntityExistsException when a product with the given name is already in teh database
     */
    @Transactional
    public Product create(Product product) {
        if(product == null){
            throw new IllegalArgumentException("Product cannot be null");
        }
        if(doesProductWithNameExist(product.getName())){
            throw new EntityExistsException("Product with name: " + product.getName() + " already exist");
        }
        logger.info("Persisting product with ID: " + product.getProduct_id());
        entityManager.persist(product);
        logger.info("Product persisted successfully " + product.getProduct_id());
        return product;
    }
    /**
     * Attempts to retrieve all products from the database
     * @return List<Product> A list of all product entities. If none were found, returns an empty list.
     */
    public List<Product> findAll(int page, int pageSize){
        logger.info("Querying to find all products.");
        TypedQuery<Product>  query = entityManager.createQuery(
                "SELECT DISTINCT p FROM Product p " +
                        "LEFT JOIN FETCH p.categories " +
                        "LEFT JOIN FETCH p.images " +
                        "LEFT JOIN FETCH p.sizes " +
                        "LEFT JOIN FETCH p.inventories ",Product.class
        )
                .setFirstResult((page-1) * pageSize)
                .setMaxResults(pageSize);

        logger.info("Successfully retrieved products");
        return query.getResultList();
    }
    /**
     * Attempts to find a product by ID.
     * @param productId Unique identifier of the product
     * @return Product on success, otherwise empty Optional
     */
    public Optional<Product> findById(long productId){
        logger.info("Querying to find product for ID: " + productId);
        TypedQuery<Product>  query = entityManager.createQuery(
                "SELECT DISTINCT p FROM Product p " +
                        "LEFT JOIN FETCH p.categories " +
                        "LEFT JOIN FETCH p.images " +
                        "LEFT JOIN FETCH p.sizes " +
                        "LEFT JOIN FETCH p.inventories " +
                        "WHERE p.product_id = :productId", Product.class
        ).setParameter("productId", productId);

        try {
            logger.info("Successfully found product for ID: " + productId);
            return Optional.ofNullable(query.getSingleResult());
        } catch( NoResultException e){
            logger.warn("Product not found with ID: " + productId);
            return Optional.empty();
        }
    }
    /**
     * @param name The name of the product to find
     * @return The product with given name when found, otherwise returns an empty Optional
     */
    public Optional<Product> findByName(String name){
        logger.info("Querying to find product for NAME: " + name);
        TypedQuery<Product>  query = entityManager.createQuery(
                "SELECT DISTINCT p FROM Product p " +
                        "LEFT JOIN FETCH p.categories " +
                        "LEFT JOIN FETCH p.images " +
                        "LEFT JOIN FETCH p.sizes " +
                        "LEFT JOIN FETCH p.inventories " +
                        "WHERE p.name = :name", Product.class
        ).setParameter("name", name);
        try {
            logger.info("Successfully found product for NAME: " + name);
            return Optional.ofNullable(query.getSingleResult());
        } catch( NoResultException e){
            logger.warn("Product not found with NAME: " + name);
            return Optional.empty();
        }
    }
    /**
     * @param product The Product entity that holds the new information to be updated
     * @return the updated product with new values
     */
    @Override
    public Product update(Product product) {
        logger.info("Updating product with ID: " + product.getProduct_id());
        Product updatedProduct = entityManager.find(Product.class, product.getProduct_id());
        if(updatedProduct != null){
            updatedProduct.setName(product.getName());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setPrice(product.getPrice());
            entityManager.merge(updatedProduct);
            logger.info("Successfully updated prodcut with ID: " + product.getProduct_id());
            return updatedProduct;
        }
        logger.error("Product with ID: " + product.getProduct_id() + " not found.");
        throw new NotFoundException("Product to updated with ID: " + product.getProduct_id() + " does not exist.");

    }
    /**
     * Attempts to delete a product by ID.
     * @param id Unique identifier of the product
     * @throws EntityNotFoundException when no product with give ID was found.
     */
    @Transactional
    public void delete(long id){
        logger.info("Deleting product with ID: " + id);
        //findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        Optional<Product> productToDelete = findById(id);

        if(productToDelete.isPresent()){
            logger.info("Deleting corresponding images");
            productToDelete.get().getImages().forEach(image -> entityManager.remove(image));
            logger.info("Deleting corresponding inventories");
            productToDelete.get().getInventories().forEach(inventory -> entityManager.remove(inventory));
        }
        entityManager.remove(productToDelete.get());
        logger.info("Product deleted successfully with ID: " + id);
    }

    public boolean doesProductWithNameExist(String name) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.name = :name", Product.class
        ).setParameter("name", name);
        if(query.getResultList().isEmpty()){
            logger.info("False");
            return false;
        }
        return true;
    }
}
