package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.UserProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
public class UserProfileRepository {
    final static Logger logger = LoggerFactory.getLogger(UserProfileRepository.class);
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Attempts to persist a userprofile for a user
     * @param userProfile data for userprofile
     * @throws IllegalArgumentException if userprofile is null
     * @throws EntityExistsException if userprofile for user already exist
     */
    @Transactional
    public void create(UserProfile userProfile){
        if(userProfile == null){
            logger.error("UserProfile cannot be null");
            throw new IllegalArgumentException("UserProfile cannot be null");
        }
        if(doesUserProfileExist(userProfile)){
            throw new EntityExistsException("UserProfile already exist for user ID: " + userProfile.getUser_id());
        }
        logger.info("Persisting userprofile for user ID: " + userProfile.getUser_id());
        entityManager.persist(userProfile);
        logger.info("Successfully persisted userprofile with ID: " + userProfile.getUserprofile_id());
    }

    /**
     *
     * @param user_id
     * @return
     * @throws NoResultException if no userprofile was found
     */
    @Transactional
    public UserProfile getByUser(long user_id){
        logger.info("Retrieving user profie for user id: " + user_id);
        TypedQuery<UserProfile> query = entityManager.createQuery(
                "SELECT up FROM UserProfile up WHERE " +
                        "up.user_id =:userId", UserProfile.class
        ).setParameter("userId", user_id);

        return query.getSingleResult();
    }

    boolean doesUserProfileExist(UserProfile userProfile){
        TypedQuery<UserProfile> query = entityManager.createQuery(
                "SELECT up FROM UserProfile up WHERE up.user_id = :user_id", UserProfile.class
        ).setParameter("user_id", userProfile.getUser_id());

        return !query.getResultList().isEmpty();
    }
}
