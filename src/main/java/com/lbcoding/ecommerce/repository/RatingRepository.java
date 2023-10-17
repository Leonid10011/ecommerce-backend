package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Rating;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class RatingRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void create(Rating rating){
        entityManager.persist(rating);
    }

    @Transactional
    public List<Rating> getByUser(Long id){
        TypedQuery<Rating> query = entityManager.createQuery(
                "SELECT r FROM Rating r WHERE r.userId = :id", Rating.class
        ).setParameter("id", id);

        List<Rating> ratingList = query.getResultList();

        if(!ratingList.isEmpty()){
            return ratingList;
        } else {
            return null;
        }
    }

    @Transactional
    public List<Rating> getByProduct(Long id){
        TypedQuery<Rating> query = entityManager.createQuery(
                "SELECT r FROM Rating r WHERE r.productId = :id", Rating.class
        ).setParameter("id", id);

        List<Rating> ratingList = query.getResultList();

        if(!ratingList.isEmpty()){
            return ratingList;
        } else {
            return null;
        }
    }

    @Transactional
    public Rating get(Long uId, Long pId){
        TypedQuery<Rating> query = entityManager.createQuery(
                "SELECT r FROM Rating r WHERE r.userId = :uId AND r.productId = :pId", Rating.class
        ).setParameter("uId", uId)
                .setParameter("pId",pId);

        List<Rating> ratingList = query.getResultList();

        if(!ratingList.isEmpty()){
            return ratingList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public Rating get(Long id){
        Rating rating = entityManager.find(Rating.class, id);

        return rating;
    }

    @Transactional
    public void delete(Long id){
        Rating rating = entityManager.find(Rating.class, id);

        if(rating != null) {
            entityManager.remove(rating);
        }
    }
}
