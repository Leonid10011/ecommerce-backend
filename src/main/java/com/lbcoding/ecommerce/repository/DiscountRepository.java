package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Discount;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DiscountRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void create(Discount discount){
        entityManager.persist(discount);
    }

    @Transactional
    public Discount get(Long id){
        Discount discount = entityManager.find(Discount.class, id);
        if(discount != null) {
            return discount;
        } else {
            return null;
        }
    }

    @Transactional
    public Discount get(String code){
        TypedQuery<Discount> query = entityManager.createQuery(
                "SELECT d FROM Discount d WHERE d.code = :code", Discount.class
        ).setParameter("code", code);

        List<Discount> discountList = query.getResultList();

        if(!discountList.isEmpty()){
            return discountList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id){
        Discount discount = entityManager.find(Discount.class, id);
        if(discount != null){
            entityManager.remove(discount);
        }
    }
}
