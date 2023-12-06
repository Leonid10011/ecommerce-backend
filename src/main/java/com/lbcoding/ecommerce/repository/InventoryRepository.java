/*
package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Inventory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class InventoryRepository {

    @PersistenceContext
    EntityManager entityManager;

    //@Transactional
    public void setQuantity(int quantity, Long productID){
        Inventory newInvetory = new Inventory();

        newInvetory.setQuantity(quantity);
        newInvetory.setProductID(productID);
        System.out.println("ERERE  " + newInvetory.getQuantity());
        entityManager.persist(newInvetory);
    }

    @Transactional
    public void update(int quantity, Long productId, Boolean operation) {

        TypedQuery<Inventory> query = entityManager.createQuery("" +
                "SELECT i FROM Inventory i WHERE i.productID = :productId", Inventory.class
        ).setParameter("productId", productId);

        Inventory existingInventory = query.getSingleResult();

        if (existingInventory != null) {
            Integer oldQuantity = existingInventory.getQuantity();

            String jpql = "UPDATE Inventory i SET i.quantity = :quantity WHERE i.productID = :productID";
            // if not enough in invetory, get the rest
            entityManager.createQuery(jpql)
                    .setParameter("quantity",
                            operation ? (oldQuantity + quantity)
                                    : (oldQuantity - quantity) >= 0 ? oldQuantity-quantity : 0)
                    .setParameter("productID", productId)
                    .executeUpdate();
        }
    }

    @Transactional
    public Inventory get(Long productID) {
        TypedQuery<Inventory> query = entityManager.createQuery(
                "SELECT i FROM Inventory i WHERE i.productID = :productID", Inventory.class);
        query.setParameter("productID", productID);

        List<Inventory> resultList = query.getResultList();

        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id){
        Inventory inventory = entityManager.find(Inventory.class, id);

        if(inventory != null){
            entityManager.remove(inventory);
        }
    }
}
*/

package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.exception.InsufficientInventoryException;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.model.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@ApplicationScoped
public class InventoryRepository {
    private static final Logger logger = LoggerFactory.getLogger(InventoryRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Persists a new inventory
     * @param inventory An Inventory entity
     * @return Inventory Entity on success.
     * @throws EntityExistsException when inventory with size ID and product ID already exist
     * @throws Exception in all other exceptional cases

    @Transactional
    public Inventory create(Inventory inventory) {
        try {
            logger.info("Persisting inventory with size ID: " + inventory.getSize_id() + " and product ID: " + inventory.getProduct_id());

            // Check if the inventory already exists
            Inventory existingInventory = entityManager.find(Inventory.class, new InventoryId(inventory.getProduct_id(), inventory.getSize_id()));
            if (existingInventory != null) {
                logger.warn("Inventory with size ID: " + inventory.getSize_id() + " and product ID: " + inventory.getProduct_id() + " already exists");
                throw new EntityExistsException("Inventory for size ID: " + inventory.getSize_id() + " and product ID: " + inventory.getProduct_id() + " already exists");
            }

            // Create and persist the new inventory
            Inventory newInventory = new Inventory();
            newInventory.setQuantity(inventory.getQuantity());
            newInventory.setLocation(inventory.getLocation());

            entityManager.persist(newInventory);
            logger.info("Successfully persisted inventory with size ID: " + inventory.getSize_id() + " and product ID: " + inventory.getProduct_id());

            return newInventory;
        } catch (Exception e) {
            // Log or handle exceptions based on your use case
            logger.error("Error creating inventory", e);
            throw e; // Re-throw the exception to propagate it up the stack
        }
    }
     */
    /**
     * Increments the quantity for a product ID. It will attempt to find an inventory, if none exists, it will create a new one
     * @param productId
     * @param quantity
     */
    @Transactional
    public void incrementQuantity(Long productId, int quantity){
        logger.info("Incrementing quantity for product ID: " + productId);
        Inventory inventory = findOrCreate(productId);
        inventory.setQuantity(inventory.getQuantity() + quantity);
        entityManager.persist(inventory);
        logger.info("Quantity incremented successfully for product ID: " + productId);
    }

    /**
     * Decrements the quantity of a product. If not sufficient products available throw error with current quantity.
     * @param productId
     * @param quantity
     * @throws InsufficientInventoryException
     */
    @Transactional
    public void decrementQuantity(Long productId, int quantity) throws InsufficientInventoryException{
        logger.info("Decrementing quantity for product ID: " + productId);
        Inventory inventory = findOrCreate(productId);
        int currentQuantity = inventory.getQuantity();

        if(quantity > currentQuantity){
            logger.info("Insufficient inventory for product ID: " + productId);
            throw new InsufficientInventoryException(
                    "Insufficient inventory for product ID" + productId,
                    currentQuantity
            );
        }

        inventory.setQuantity(currentQuantity - quantity);
        entityManager.merge(inventory);
        logger.info("Quantity decremented successfully for product ID: " + productId);
    }

    public Inventory findOrCreate(Long productId) {
        logger.info("Finding inventory for product ID: " + productId);
        return findByProductId(productId).orElseGet(() -> {
            logger.info("Inventory not found for product ID: " + productId);
            logger.info("Persisting new inventory for product ID: " + productId);
            Inventory newInventory = new Inventory();
            //newInventory.setProduct_id(productId);
            newInventory.setQuantity(0);
            entityManager.persist(newInventory);
            logger.info("Inventory persisted successfully for product ID: " + productId);
            return newInventory;
        });
    }

    @Transactional
    public Optional<Inventory> findByProductId(Long productId) {
        logger.info("Querying find by product ID: " + productId);
        TypedQuery<Inventory> query = entityManager.createQuery(
                        "SELECT i FROM Inventory i WHERE i.productID = :productID", Inventory.class)
                .setParameter("productID", productId);
        try {
            Inventory inventory = query.getSingleResult();
            logger.info("Category found by product ID " + productId);
            return Optional.ofNullable(inventory);
        } catch ( NoResultException e) {
            logger.info("Category not found for productId ID "+ productId);
            return Optional.empty();
        }
    }
    public Optional<Inventory> findById(Long id){
        logger.info("Finding inventory by ID: " + id);
        Inventory inventory = entityManager.find(Inventory.class, id);
        if(inventory != null){
            logger.info("Inventory found by ID: " + id);
            return Optional.of(inventory);
        }
        logger.info("Inventory not found by ID: " + id);
        return Optional.empty();
    }

    /**
     * Attemps to delete inventory by id. If not found, throws an NotFoundException
     * @param id- The inventory's unique identifier
     */
    @Transactional
    public void delete(Long id) {
        logger.info("Deleting inventory by ID: " + id);
        Inventory inventory = findById(id).orElseThrow(() -> new NotFoundException("Inventory not found by ID: " + id));
        entityManager.remove(inventory);
        logger.info("Inventory deleted successfully");
    }
    @Transactional
    public void setInventoryForProduct(Product product, int quantity){
        logger.info("Setting inventory for product with ID: " + product.getProduct_id());
        product.getSizes().forEach(size -> {
            Inventory inventory = new Inventory();
            inventory.setProduct_id(product.getProduct_id());
            inventory.setSize_id(size.getSize_id());
            inventory.setLocation("Test");
            inventory.setQuantity(100);
            inventory.setProduct(product);
            inventory.setSize(size);

            entityManager.persist(inventory);
        });
        logger.info("Successfully set inventory for product");
    }

}