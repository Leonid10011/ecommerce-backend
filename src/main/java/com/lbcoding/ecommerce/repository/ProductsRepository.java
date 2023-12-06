package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.repository.interfaces.IProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
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
    public List<Product> findAll(){
        logger.info("Querying to find all products");
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p", Product.class
        );

        List<Product> products = query.getResultList();
        if(products.isEmpty()){
            logger.info("Products is empty");
            return products;
        }
        logger.info("Products found successfully");
        return products;
    }

    /**
     * @param name The name of the product to find
     * @return The product with given name when found, otherwise returns an empty Optional
     */
    @Override
    public Optional<Product> findByName(String name) {
        logger.info("Finding Product by NAME: " + name);
        TypedQuery<Product> query = entityManager.createQuery("" +
                "SELECT p FROM Product p WHERE p.name = :name", Product.class)
                .setParameter("name", name);

        Optional<Product> product = Optional.ofNullable(query.getSingleResult());
        if(product.isEmpty())
            logger.info("Successfully found product with ID: " + product.get().getProduct_id());
        logger.info("Failed to find product with ID: " + product.get().getProduct_id());
        return product;
    }

    /**
     * @param id The ID of the product to find
     * @return
     */
    @Override
    public Optional<Product> findById(long id) {
        logger.info("Finding Product by ID: " + id);
        Product product = entityManager.find(Product.class, id);

        if(product == null){
            logger.info("Failed to find product with ID: " + id);
            return Optional.empty();
        }

        logger.info("Successfully found product with ID: " + product.getProduct_id());
        return Optional.of(product);
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
     * @param id The of the product to be deleted
     */
    @Override
    public void delete(long id) {
        logger.info("Deleting product with ID: " + id);
        Product product = entityManager.find(Product.class, id);
        if(product != null){
            entityManager.remove(product);
            logger.info("Successfully deleted product with ID " + id);
        }

        logger.info("Product does not exist with ID: " + id + ". Nothing to delete");
    }

    /**
     * Attempts to find a product by ID.
     * @param id Unique identifier of the product
     * @return Product on success, otherwise empty Optional
     */
    public Optional<Product> findById(Long id){
        logger.info("Querying find product with ID: " + id);
        Product product = entityManager.find(Product.class, id);
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
     * @throws EntityNotFoundException when no product with give ID was found.
     */
    public void delete(Long id){
        logger.info("Deleting product with ID: " + id);
        findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
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
