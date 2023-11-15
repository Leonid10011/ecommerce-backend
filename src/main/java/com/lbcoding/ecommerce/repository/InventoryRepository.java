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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class InventoryRepository {
    private static final Logger logger = LoggerFactory.getLogger(InventoryRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void create(Inventory inventory) {
        entityManager.persist(inventory);
    }

    public void incrementQuantity(Long productId, int quantity){
        Inventory inventory = findOrCreate(productId);
        inventory.setQuantity(inventory.getQuantity() + quantity);
    }

    /**
     * Decrements the quantity of a product. If not sufficient products available throw error with current quantity.
     * @param productId
     * @param quantity
     * @throws InsufficientInventoryException
     */
    public void decrementQuantity(Long productId, int quantity) throws InsufficientInventoryException{
        Inventory inventory = findOrCreate(productId);
        int currentQuantity = inventory.getQuantity();

        if(quantity > currentQuantity){
            throw new InsufficientInventoryException(
                    "Insufficient inventory for product ID" + productId,
                    currentQuantity
            );
        }

        inventory.setQuantity(currentQuantity - quantity);
        entityManager.merge(inventory);
    }

    public Inventory findOrCreate(Long productId) {
        return findByProductId(productId).orElseGet(() -> {
            Inventory newInventory = new Inventory();
            newInventory.setProductId(productId);
            newInventory.setQuantity(0);
            entityManager.persist(newInventory);
            return newInventory;
        });
    }

    @Transactional
    public Optional<Inventory> findByProductId(Long productId) {
        TypedQuery<Inventory> query = entityManager.createQuery(
                        "SELECT i FROM Inventory i WHERE i.productID = :productID", Inventory.class)
                .setParameter("productID", productId);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch ( NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void delete(Long id) {
        Inventory inventory = entityManager.find(Inventory.class, id);

        if (inventory != null) {
            entityManager.remove(inventory);
        } else {
            logger.error("Inventory not found");
        }
    }
}