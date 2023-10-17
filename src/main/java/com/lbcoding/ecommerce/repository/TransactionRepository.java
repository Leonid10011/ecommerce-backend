package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Transaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TransactionRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void create(Transaction transaction){
        entityManager.persist(transaction);
    }

    @Transactional
    public Transaction get(Long id){
        Transaction transaction = entityManager.find(Transaction.class, id);

        return transaction;
    }

    @Transactional
    public Transaction getByOrder(Long orderId){
        TypedQuery<Transaction> query = entityManager.createQuery(
                "SELECT t FROM Transaction t WHERE t.orderId = :orderId", Transaction.class
        ).setParameter("orderId", orderId);

        List<Transaction> transactionList = query.getResultList();

        if(!transactionList.isEmpty()){
            return transactionList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id){
        Transaction transaction = entityManager.find(Transaction.class, id);

        if(transaction != null){
            entityManager.remove(transaction);
        }
    }
}
