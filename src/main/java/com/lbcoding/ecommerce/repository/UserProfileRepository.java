package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.UserProfile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class UserProfileRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void create(UserProfile userProfile){
        entityManager.persist(userProfile);
    }

    @Transactional
    public Optional<UserProfile> findById(Long id){
        Optional<UserProfile> userProfile = Optional.ofNullable(entityManager.find(UserProfile.class, id));

        return userProfile.isEmpty() ? Optional.empty() : userProfile;
    }

    @Transactional
    public Optional<UserProfile> findByUserId(Long userId) {
        TypedQuery<UserProfile> query = entityManager.createQuery(
                "SELECT up FROM UserProfile up WHERE up.userId = :userId", UserProfile.class
        ).setParameter("userId", userId);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Transactional
    public void delete(Long id){
        Optional<UserProfile> userProfile = findById(id);

        userProfile.ifPresent(entityManager::remove);
    }
}
