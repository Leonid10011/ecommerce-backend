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

import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.repository.interfaces.IInventoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class InventoryRepository implements IInventoryRepository {
    private static final Logger logger = LoggerFactory.getLogger(InventoryRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    /** Persists a new inventory in the database
     * @param inventory Inventory with necessary data
     * @throws EntityExistsException If inventory for the product, size and location combination already exist.
     */
    @Override
    @Transactional
    public void create(Inventory inventory) {
        if(inventory == null){
            throw new IllegalArgumentException("Inventory cannot be null");
        }
        // Check if an inventory for the given combination of product, size and location already exist.
        if(doesInventoryExist(inventory)){
            throw new EntityExistsException("Inventory for product ID: " + inventory.getProduct_id() + ", size ID: " + inventory.getSize_id() + " and location " + inventory.getLocation() + " already exist");
        }
        logger.info("Persisting inventory");
        entityManager.persist(inventory);
        logger.info("Successfully persisted inventory with ID: " + inventory.getInventory_id());
    }
    @Override
    @Transactional
    public List<Inventory> findByProduct(long productId) {
        logger.info("Querying find by product ID: " + productId);
        TypedQuery<Inventory> query = entityManager.createQuery(
                        "SELECT i FROM Inventory i WHERE i.productID = :productID", Inventory.class)
                .setParameter("productID", productId);
        List<Inventory> inventories = query.getResultList();
        logger.info("Successfully found inventories for product ID: " + productId);
        return inventories;
    }

    /** Finds the inventories that contain a prodcut with a specific size
     * @param product_id the ID of the product
     * @param size_id the ID of the size of the product
     * @return a List<Inventory>. If none were found List is empty. Handling up to the client.
     */
    @Override
    public List<Inventory> findByProductAndSize(long product_id, long size_id) {
        logger.info("Querying find inventory by product and size");
        TypedQuery<Inventory> query = entityManager.createQuery(
                "SELECT i FROM Inventory i WHERE i.product_id =:p_id AND i.size_id =:s_id", Inventory.class
        ).setParameter("p_id", product_id)
                .setParameter("s_id", size_id);
        logger.info("Successfully found inventories");
        return query.getResultList();
    }

    /** Updates `{location}` and `{quantity}` of a given inventory in the database.
     * @param inventory Inventory model with the new values
     */
    @Override
    public void update(Inventory inventory) {
        Inventory updatedInventory = entityManager.find(Inventory.class, inventory.getInventory_id());
        if(updatedInventory == null){
            throw new NotFoundException("Inventory with ID: " + inventory.getInventory_id() + " does not exist\n Cannot update");
        }
        updatedInventory.setLocation(inventory.getLocation());
        updatedInventory.setQuantity(inventory.getQuantity());
        entityManager.merge(updatedInventory);
    }

    /**
     * Finds an invnentory by ID
     * @param id The ID of the inventory to find
     * @return An Optional<Inventory> if found, otherwise an empty Optional.
     */
    public Optional<Inventory> findById(long id){
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
    @Override
    @Transactional
    public void delete(long id) {
        logger.info("Deleting inventory by ID: " + id);
        Inventory inventory = findById(id).orElseThrow(() -> new NotFoundException("Inventory not found by ID: " + id));
        entityManager.remove(inventory);
        logger.info("Inventory deleted successfully");
    }
    @Override
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

    private boolean doesInventoryExist(Inventory inventory){
        TypedQuery<Inventory> query = entityManager.createQuery(
                "SELECT i FROM Inventory i WHERE i.product_id =:p_id AND i.size_id =:s_id AND i.location =:loc", Inventory.class
        ).setParameter("p_id", inventory.getProduct_id())
                .setParameter("s_id", inventory.getSize_id())
                .setParameter("loc", inventory.getLocation());

        return !query.getResultList().isEmpty();
    }
}