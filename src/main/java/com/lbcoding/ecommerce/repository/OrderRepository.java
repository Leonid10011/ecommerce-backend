package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Order;
import com.lbcoding.ecommerce.model.OrderItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class OrderRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void create(Order order){
        entityManager.persist(order);
    }

    @Transactional
    public List<Order> get(){
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o", Order.class
        );

        List<Order> orderList = query.getResultList();

        return orderList;
    }

    @Transactional
    public Order get(Long id){
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.id = :id", Order.class
        ).setParameter("id", id);

        List<Order> orderList = query.getResultList();

        if(!orderList.isEmpty()){
            return orderList.get(0);
        }else {
            return null;
        }
    }

    @Transactional
    public Order getByUser(Long id){
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.userId = :id", Order.class
        ).setParameter("id", id);

        List<Order> orderList = query.getResultList();

        if(!orderList.isEmpty()){
            return orderList.get(0);
        }else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id){
        Order order = entityManager.find(Order.class, id);

        if(order != null){
            entityManager.remove(order);
        }
    }
}
