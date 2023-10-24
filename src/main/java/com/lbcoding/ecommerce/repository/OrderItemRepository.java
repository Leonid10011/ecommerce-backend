package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.OrderItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class OrderItemRepository {
    @Inject
    EntityManager entityManager;

    @Transactional
    public void create(OrderItem orderItem){
        entityManager.persist(orderItem);
    }

    @Transactional
    public void delete(Long id){
        OrderItem orderItem = entityManager.find(OrderItem.class, id);
        if(orderItem != null){
            entityManager.remove(orderItem);
        }
    }

    @Transactional
    public List<OrderItem> get(){
        TypedQuery<OrderItem> query = entityManager.createQuery(
                "SELECT oi FROM OrderItem io", OrderItem.class
        );

        List<OrderItem> orderItemList = query.getResultList();

        if(!orderItemList.isEmpty()){
            return orderItemList;
        }
        else return null;
    }

    @Transactional
    public List<OrderItem> get(Long orderId){
        TypedQuery<OrderItem> query = entityManager.createQuery(
                "SELECT oi FROM OrderItem oi WHERE oi.orderId = : orderId", OrderItem.class
        ).setParameter("orderId", orderId);

        List<OrderItem> orderItemList = query.getResultList();

        if(!orderItemList.isEmpty()){
            return orderItemList;
        } else {
            return null;
        }
    }

    @Transactional
    public OrderItem getbById(Long id){
        OrderItem orderItem = entityManager.find(OrderItem.class, id);

        if(orderItem != null){
            return orderItem;
        }else {
            return null;
        }
    }

    @Transactional
    public OrderItem getByProduct(Long productId){
        TypedQuery<OrderItem> query = entityManager.createQuery(
                "SELECT oi FROM OrderItem oi WHERE oi.productId = : productId", OrderItem.class
        ).setParameter("productId", productId);

        List<OrderItem> orderItemList = query.getResultList();

        if(!orderItemList.isEmpty()){
            return orderItemList.get(0);
        } else {
            return null;
        }
    }
}
