package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.BuyOrder;
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
    public void create(BuyOrder order){
        entityManager.persist(order);
    }

    @Transactional
    public List<BuyOrder> get(){
        TypedQuery<BuyOrder> query = entityManager.createQuery(
                "SELECT o FROM Order o", BuyOrder.class
        );

        List<BuyOrder> orderList = query.getResultList();

        return orderList;
    }

    @Transactional
    public BuyOrder get(Long id){
        TypedQuery<BuyOrder> query = entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.id = :id", BuyOrder.class
        ).setParameter("id", id);

        List<BuyOrder> orderList = query.getResultList();

        if(!orderList.isEmpty()){
            return orderList.get(0);
        }else {
            return null;
        }
    }

    @Transactional
    public BuyOrder getByUser(Long id){
        TypedQuery<BuyOrder> query = entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.userId = :id", BuyOrder.class
        ).setParameter("id", id);

        List<BuyOrder> orderList = query.getResultList();

        if(!orderList.isEmpty()){
            return orderList.get(0);
        }else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id){
        BuyOrder order = entityManager.find(BuyOrder.class, id);

        if(order != null){
            entityManager.remove(order);
        }
    }
}
