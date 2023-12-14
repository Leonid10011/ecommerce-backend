package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.OrderItem;
import com.lbcoding.ecommerce.model.compositeKey.OrderItemId;
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
    public Optional<OrderItem> findById(OrderItemId orderItemId){
        logger.info("Searching for orderItem by order ID: " + orderItemId.getOrder_id() + " and product ID: " + orderItemId.getProduct_id());
        OrderItem orderItem = entityManager.find(OrderItem.class, orderItemId);
        if(orderItem == null){
            return Optional.empty();
        }
        return Optional.of(orderItem);
    }
    /**
     * Attempts to retrieve order items for an order
     * @param id the id of the order
     * @return List of order items. Empty if none were found
     */
    public List<OrderItem> findByOrder(long id){
        logger.info("Searching order items for order ID: " + id);
        TypedQuery<OrderItem> query = entityManager.createQuery(
                "SELECT o FROM OrderItem o WHERE o.orderItem_id.order_id = :id", OrderItem.class
        ).setParameter("id", id);

        return query.getResultList();
    }
    @Transactional
    public OrderItem update(OrderItem orderItem){
        logger.info("Updating orderItem with order ID: " + orderItem.getOrderItem_id().getOrder_id() + " product ID: " + orderItem.getOrderItem_id().getOrder_id());
        OrderItem updatedOrderItem = entityManager.find(OrderItem.class, orderItem.getOrderItem_id());
        if(updatedOrderItem == null){
            throw new NotFoundException("Order item not found for product ID: " + orderItem.getOrderItem_id().getProduct_id() + " and order ID: " + orderItem.getOrderItem_id().getOrder_id());
        }
        updatedOrderItem.setQuantity(orderItem.getQuantity());
        entityManager.persist(updatedOrderItem);
        return updatedOrderItem;
    }

    /**
     * Deletes orderItem by ID
     * @param id id of the composite orderItem (orderId, productId)
     */
    @Transactional
    public void delete(OrderItemId id){
        logger.info("Deleting orderItem with ID: " + id);
        Optional<OrderItem> orderItem = findById(id);
        orderItem.ifPresent(entityManager::remove);
        logger.info("Successfully removed orderItem with ID: " + id);
    }
    /**
     * Checks if order items exists in the database
     * @param orderItem the order item data to search
     * @return boolean
     */
    boolean doesOrderItemExist(OrderItem orderItem){
        TypedQuery<OrderItem> query = entityManager.createQuery(
                "SELECT oi FROM OrderItem oi WHERE " +
                        "oi.orderItem_id.order_id = :orderId AND " +
                        "oi.orderItem_id.product_id = :productId AND " +
                        "oi.size_name = :size_name", OrderItem.class
        ).setParameter("orderId", orderItem.getOrderItem_id().getOrder_id())
                .setParameter("productId", orderItem.getOrderItem_id().getProduct_id())
                .setParameter("size_name", orderItem.getSize_name());

        return !query.getResultList().isEmpty();
    }
}
