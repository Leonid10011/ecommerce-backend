package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.OrderItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrderItemRepository {
    @Inject
    private EntityManager entityManager;

    @Transactional
    public void create(OrderItem orderItem) {
        entityManager.persist(orderItem);
    }

    @Transactional
    public void delete(Long id) {
        OrderItem orderItem = entityManager.find(OrderItem.class, id);
        if (orderItem != null) {
            entityManager.remove(orderItem);
        }
    }

    @Transactional
    public List<OrderItem> getAllOrderItems() {
        TypedQuery<OrderItem> query = entityManager.createQuery(
                "SELECT oi FROM OrderItem oi", OrderItem.class
        );

        return query.getResultList();
    }

    @Transactional
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        TypedQuery<OrderItem> query = entityManager.createQuery(
                "SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId", OrderItem.class
        ).setParameter("orderId", orderId);

        return query.getResultList();
    }

    @Transactional
    public Optional<OrderItem> getOrderItemById(Long id) {
        OrderItem orderItem = entityManager.find(OrderItem.class, id);
        return Optional.ofNullable(orderItem);
    }

    @Transactional
    public Optional<OrderItem> getOrderItemByProductId(Long productId) {
        TypedQuery<OrderItem> query = entityManager.createQuery(
                "SELECT oi FROM OrderItem oi WHERE oi.productId = :productId", OrderItem.class
        ).setParameter("productId", productId);

        List<OrderItem> orderItemList = query.getResultList();
        return orderItemList.isEmpty() ? Optional.empty() : Optional.of(orderItemList.get(0));
    }
}
