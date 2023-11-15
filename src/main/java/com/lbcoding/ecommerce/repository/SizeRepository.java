package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Sizes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class SizeRepository {
    @Inject
    EntityManager entityManager;
    @Transactional
    public void create(Sizes size){
        entityManager.persist(size);
    }

    @Transactional
    public Optional<Sizes> findById(Long id){
        return Optional.ofNullable(entityManager.find(Sizes.class, id));
    }

    @Transactional
    public Optional<Sizes> findByDescription(String description){
        TypedQuery<Sizes> query = entityManager.createQuery(
                "SELECT s FROM Size s WHERE s.sizeDescription = :description", Sizes.class
        ).setParameter("description", description);

        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch( NoResultException e ) {
            return Optional.empty();
        }
    }

    @Transactional
    public void delete(Long id){
        findById(id).ifPresent(item -> entityManager.remove(item));
    }
}
