package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.model.Rating;
import com.lbcoding.ecommerce.model.User;
import com.lbcoding.ecommerce.model.compositeKey.RatingId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RatingRepository {
    private static final Logger logger = LoggerFactory.getLogger(RatingRepository.class);
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void create(Rating rating){
        entityManager.persist(rating);
    }

    /**
     * Sets the rating values for a product
     * @param rating Rating model
     * @throws EntityExistsException if the rating with RatingId already exist
     * @throws IllegalArgumentException if rating is null
     */
    @Transactional
    public void setRatingForProduct(Rating rating){
        if(rating == null) {
            throw new IllegalArgumentException("Rating cannot be null");
        }

        if(doesRatingExist(rating)){
            throw new EntityExistsException("Rating with IDs already exist");
        }

        logger.info("Persisting Rating for product with ID: " + rating.getRating_id().getProduct_id());
        entityManager.persist(rating);
        logger.info("Successfully set rating for product with ID: " + rating.getRating_id().getProduct_id());
    }

    /**
     * Retrieves the rating belonging to the user ID.
     * @param id The unique ID of the corresponding user.
     * @return List of all rating if exists, otherwise return empty list.
     */
    @Transactional
    public List<Rating> getByUser(Long id){
        TypedQuery<Rating> query = entityManager.createQuery(
                "SELECT r FROM Rating r WHERE r.user_id = :id", Rating.class
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
        logger.info("Searching rating for product ID: " + id);
        TypedQuery<Rating> query = entityManager.createQuery(
                "SELECT r FROM Rating r WHERE r.product.product_id = :id", Rating.class
        ).setParameter("id", id);
        List<Rating> ratings = query.getResultList();
        logger.info(String.valueOf(ratings.get(0).getProduct().getCategories().stream().toArray()[0].getClass().getName()));
        logger.info("Successfully found ratings");
        return ratings;
    }

    /**
     * Retrieve the unique rating that was made by a user for a product.
     * @param ratingId containing user ID and product ID
     * @return An Optional<Rating> when found, otherwise Optional.empty()
     */
    @Transactional
    public Optional<Rating> get(RatingId ratingId){
        TypedQuery<Rating> query = entityManager.createQuery(
                "SELECT r FROM Rating r WHERE r.userId = :uId AND r.productId = :pId", Rating.class
        ).setParameter("uId", ratingId.getUser_id())
                .setParameter("pId", ratingId.getProduct_id());
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch( NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieve the average rating_value for a product based on all ratings made
     * @param product_id the product for the rating value
     * @return the average rating value as double
     */
    @Transactional
    public double getRatingValueForProduct(long product_id){
        logger.info("Getting Rating value for product with ID: " + product_id);
        TypedQuery<Double> query = entityManager.createQuery(
                "SELECT CAST(SUM(r.rating_value) AS DOUBLE) FROM Rating r WHERE r.product.product_id = :product_id", Double.class
        ).setParameter("product_id", product_id);

        try {
            return query.getSingleResult();
        } catch( NoResultException e){
            logger.warn("No ratings found for product with ID: " + product_id + ". Setting value to 0.0");
            return 0.0;
        }
    }
    /**
     *  Deletes a rating by its unique ID.
     * @param id The unique identifier of the rating.
     */
    @Transactional
    public void delete(RatingId id){
        logger.info("Deleting rating with IDs product_id: " + id.getProduct_id() + " user_id: " + id.getUser_id());
        Rating rating = entityManager.find(Rating.class, id);

        if(rating != null) {
            entityManager.remove(rating);
            logger.info("Successfully removed rating");
        }
    }

    private boolean doesRatingExist(Rating rating){
        logger.info("Querying to find rating by IDs " + rating.getRating_id().getProduct_id() + " " + rating.getRating_id().getUser_id());
        if(!doesProductAndUserExist(rating)){
            throw new NotFoundException("Invalid Product or User rating");
        }
        TypedQuery<Rating> query = entityManager.createQuery(
                "SELECT r FROM Rating r WHERE r.rating_id.product_id = :pId AND r.rating_id.user_id = :uId", Rating.class
        ).setParameter("pId", rating.getRating_id().getProduct_id())
                .setParameter("uId", rating.getRating_id().getUser_id());

        return !query.getResultList().isEmpty();
    }

    private boolean doesProductAndUserExist(Rating rating){
        Product product = entityManager.find(Product.class, rating.getRating_id().getProduct_id());
        if(product == null ){
            return false;
        }
        User user = entityManager.find(User.class, rating.getRating_id().getUser_id());
        return user != null;
    }
}
