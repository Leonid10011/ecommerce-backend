package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Rating;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RatingRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void create(Rating rating){
        entityManager.persist(rating);
    }

    /**
     * Retrieves the rating belonging to the user ID.
     * @param id The unique ID of the corresponding user.
     * @return List of all rating if exists, otherwise return empty list.
     */
    @Transactional
    public List<Rating> getByUser(Long id){
        TypedQuery<Rating> query = entityManager.createQuery(
                "SELECT r FROM Rating r WHERE r.userId = :id", Rating.class
        ).setParameter("id", id);

        return query.getResultList();
    }

    /**
     * Retrieves a list of rating belonging to the product ID.
     * @param id The unique identifier of the product.
     * @return Returns a list of all ratings, otherwise returns an empty list.
     */
    @Transactional
    public List<Rating> getByProduct(Long id){
        TypedQuery<Rating> query = entityManager.createQuery(
                "SELECT r FROM Rating r WHERE r.productId = :id", Rating.class
        ).setParameter("id", id);

        return query.getResultList();
    }

    /**
     * Retrieve the unique rating that was made by a user for a product.
     * @param uId The unique identifier of the user.
     * @param pId The unique identifier of the product.
     * @return
     */
    @Transactional
    public Optional<Rating> get(Long uId, Long pId){
        TypedQuery<Rating> query = entityManager.createQuery(
                "SELECT r FROM Rating r WHERE r.userId = :uId AND r.productId = :pId", Rating.class
        ).setParameter("uId", uId)
                .setParameter("pId",pId);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch( NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieve a rating by its unique ID.
     * @param id The unique identifier of the rating.
     * @return returns the rating if successful, otherwise returns null.
     */
    @Transactional
    public Optional<Rating> get(Long id){
        return Optional.ofNullable(entityManager.find(Rating.class, id));
    }

    /**
     *  Deletes a rating by its unique ID.
     * @param id The unique identifier of the rating.
     */
    @Transactional
    public void delete(Long id){
        Rating rating = entityManager.find(Rating.class, id);

        if(rating != null) {
            entityManager.remove(rating);
        }
    }

    @Transactional
    public void updateRatingValue(Long id, int newRating) {
        Rating rating = entityManager.find(Rating.class, id);
        if (rating != null) {
            rating.setRating(newRating);
            entityManager.merge(rating);
        }
    }

    @Transactional
    public void updateRatingText(Long id, String newText) {
        Rating rating = entityManager.find(Rating.class, id);
        if (rating != null) {
            rating.setText(newText);
            entityManager.merge(rating);
        }
    }


}
