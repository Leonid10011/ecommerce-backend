package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Category;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.service.ProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;
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
