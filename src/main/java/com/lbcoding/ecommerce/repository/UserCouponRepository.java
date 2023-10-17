package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Discount;
import com.lbcoding.ecommerce.model.User;
import com.lbcoding.ecommerce.model.UserCoupon;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class UserCouponRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void create(UserCoupon userCoupon){
        entityManager.persist(userCoupon);
    }

    @Transactional
    public List<UserCoupon> getByUser(Long id){
        TypedQuery<UserCoupon> query = entityManager.createQuery(
                "SELECT uc FROM UserCoupon uc WHERE uc.userId = :id", UserCoupon.class
        ).setParameter("id", id);

        List<UserCoupon> userCouponList = query.getResultList();

        if(!userCouponList.isEmpty()){
            return userCouponList;
        } else {
            return null;
        }
    }

    @Transactional
    public List<UserCoupon> getByDiscount(Long id){
        TypedQuery<UserCoupon> query = entityManager.createQuery(
                "SELECT uc FROM UserCoupon uc WHERE uc.discountId = :id", UserCoupon.class
        ).setParameter("id", id);

        List<UserCoupon> userCouponList = query.getResultList();

        if(!userCouponList.isEmpty()){
            return userCouponList;
        } else {
            return null;
        }
    }

    @Transactional
    public UserCoupon get(Long id){
        UserCoupon userCoupon = entityManager.find(UserCoupon.class, id);

        return userCoupon;
    }

    @Transactional
    public UserCoupon get(Long uId, Long dId){
        TypedQuery<UserCoupon> query = entityManager.createQuery(
                "SELECT uc FROM UserCoupon uc WHERE uc.userId = :uId AND uc.discountId = :dId", UserCoupon.class
        ).setParameter("uId", uId)
                .setParameter("dId",dId);

        List<UserCoupon> userCouponList = query.getResultList();

        if(!userCouponList.isEmpty()){
            return userCouponList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id){
        UserCoupon userCoupon = entityManager.find(UserCoupon.class, id);

        if(userCoupon != null)
            entityManager.remove(userCoupon);
    }

}
