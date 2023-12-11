package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.OrderItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class OrderItemRepository {
    final static Logger logger = LoggerFactory.getLogger(OrderItemRepository.class);
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Attempts to persist an orderItem for product and user
     * @param orderItem contains order data to persist
     * @throws IllegalArgumentException when orderItem is null
     * @throws EntityExistsException when orderItem for order and product already exist
     */
    @Transactional
    public void create(OrderItem orderItem){
        if(orderItem == null){
            throw new IllegalArgumentException("OrderItem cannot be null");
        }
        logger.info("Persisting orderItem");
        if(doesOrderItemExist(orderItem)){
            throw new EntityExistsException("Order item product ID: " + orderItem.getOrderItem_id().getProduct_id() + " and order ID: " + orderItem.getOrderItem_id().getOrder_id() + " already exist");
        }
        entityManager.persist(orderItem);
        logger.info("Successfully persisted orderItem");
    }

    boolean doesOrderItemExist(OrderItem orderItem){
        TypedQuery<OrderItem> query = entityManager.createQuery(
                "SELECT oi FROM OrderItem oi WHERE " +
                        "oi.order_id = :orderId AND " +
                        "oi.product_id = :productId", OrderItem.class
        ).setParameter("orderId", orderItem.getOrderItem_id().getOrder_id())
                .setParameter("productId", orderItem.getOrderItem_id().getProduct_id());

        return !query.getResultList().isEmpty();
    }
}
