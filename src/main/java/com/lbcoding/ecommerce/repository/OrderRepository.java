package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void create(Order order) {
        entityManager.persist(order);
    }

    @Transactional
    public List<Order> getAllOrders() {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o", Order.class
        );

        return query.getResultList();
    }

    @Transactional
    public Optional<Order> getOrderById(Long id) {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.id = :id", Order.class
        ).setParameter("id", id);

        List<Order> orderList = query.getResultList();

        return orderList.isEmpty() ? Optional.empty() : Optional.of(orderList.get(0));
    }

    @Transactional
    public Optional<Order> getOrderByUser(Long id) {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.userId = :id", Order.class
        ).setParameter("id", id);

        List<Order> orderList = query.getResultList();

        return orderList.isEmpty() ? Optional.empty() : Optional.of(orderList.get(0));
    }

    @Transactional
    public void deleteOrder(Long id) {
        Optional<Order> order = getOrderById(id);

        order.ifPresent(entityManager::remove);
    }
}
