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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class InventoryRepository {
    private static final Logger logger = LoggerFactory.getLogger(InventoryRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void setQuantity(int quantity, Long productId) {
        Inventory newInventory = new Inventory();
        newInventory.setQuantity(quantity);
        newInventory.setProductID(productId);

        entityManager.persist(newInventory);
    }

    @Transactional
    public void update(int quantity, Long productId, boolean isIncrement) {
        TypedQuery<Inventory> query = entityManager.createQuery(
                        "SELECT i FROM Inventory i WHERE i.productID = :productId", Inventory.class)
                .setParameter("productId", productId);

        try {
            Inventory existingInventory = query.getSingleResult();
            if (existingInventory != null) {
                int oldQuantity = existingInventory.getQuantity();

                int newQuantity = isIncrement ? oldQuantity + quantity : Math.max(oldQuantity - quantity, 0);

                String jpql = "UPDATE Inventory i SET i.quantity = :quantity WHERE i.productID = :productID";
                entityManager.createQuery(jpql)
                        .setParameter("quantity", newQuantity)
                        .setParameter("productID", productId)
                        .executeUpdate();
            }
        } catch (Exception e) {
            logger.error("Fehler beim Aktualisieren des Inventars: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Inventory findByProductId(Long productId) {
        TypedQuery<Inventory> query = entityManager.createQuery(
                        "SELECT i FROM Inventory i WHERE i.productID = :productID", Inventory.class)
                .setParameter("productID", productId);

        List<Inventory> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Transactional
    public void delete(Long id) {
        Inventory inventory = entityManager.find(Inventory.class, id);

        if (inventory != null) {
            entityManager.remove(inventory);
        }
    }
}