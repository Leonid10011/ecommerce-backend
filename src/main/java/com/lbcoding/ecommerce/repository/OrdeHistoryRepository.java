package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.OrderHistory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class OrdeHistoryRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void create(OrderHistory orderHistory){
        entityManager.persist(orderHistory);
    }

    @Transactional
    public OrderHistory get(Long id){
        OrderHistory orderHistory = entityManager.find(OrderHistory.class, id);

        if(orderHistory != null){
            return orderHistory;
        }

        return null;
    }

    @Transactional
    public List<OrderHistory > getByUser(Long id){
        TypedQuery<OrderHistory> query = entityManager.createQuery(
                "SELECT oh FROM OrderHistory oh WHERE oh.userId = :id", OrderHistory.class
        ).setParameter("id", id);

        List<OrderHistory> orderHistoryList = query.getResultList();

        if(!orderHistoryList.isEmpty()){
            return orderHistoryList;
        } else {
            return null;
        }
    }

    @Transactional
    public List<OrderHistory > getByOrder(Long id){
        TypedQuery<OrderHistory> query = entityManager.createQuery(
                "SELECT oh FROM OrderHistory oh WHERE oh.orderId = :id", OrderHistory.class
        ).setParameter("id", id);

        List<OrderHistory> orderHistoryList = query.getResultList();

        if(!orderHistoryList.isEmpty()){
            return orderHistoryList;
        } else {
            return null;
        }
    }

    /**
     * We ensure that only one unique combination of user and order exists in the service
     * @param uId
     * @param oId
     * @return OrderHistory
     */
    @Transactional
    public OrderHistory get(Long uId, Long oId){
        TypedQuery<OrderHistory> query = entityManager.createQuery(
                "SELECT oh FROM OrderHistory oh WHERE oh.userId = : uId AND oh.orderId = :oId", OrderHistory.class
        ).setParameter("uId", uId)
                .setParameter("oId", oId);

        List<OrderHistory> orderHistoryList = query.getResultList();

        if(!orderHistoryList.isEmpty()){
            return orderHistoryList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id){
        OrderHistory orderHistory = entityManager.find(OrderHistory.class, id);

        if(orderHistory != null){
            entityManager.remove(orderHistory);
        }
    }
}
