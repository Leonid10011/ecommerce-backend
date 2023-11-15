package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Sizes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@ApplicationScoped
public class SizesRepository {
    private static final Logger logger = LoggerFactory.getLogger(InventoryRepository.class);
    @Inject
    EntityManager entityManager;
    @Transactional
    public void create(Sizes size){
        if(size == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }
        logger.info("Persisting new size");
        entityManager.persist(size);
        logger.info("Size persisted successfully with ID: " + size.getId());
    }

    @Transactional
    public Optional<Sizes> findById(Long id){
        return Optional.ofNullable(entityManager.find(Sizes.class, id));
    }

    @Transactional
    public Optional<Sizes> findByDescription(String description){
        logger.info("Querying for size by description");
        TypedQuery<Sizes> query = entityManager.createQuery(
                "SELECT s FROM Size s WHERE s.sizeDescription = :description", Sizes.class
        ).setParameter("description", description);

        try {
            Sizes result = query.getSingleResult();
            logger.info("Size found for description: " + description);
            return Optional.of(result);
        } catch( NoResultException e ) {
            logger.info("No size found for description: " + description);
            return Optional.empty();
        }
    }

    @Transactional
    public void delete(Long id){
        logger.info("Deleting size with ID: " + id);
        Sizes size = findById(id).orElseThrow(() ->new EntityNotFoundException("Size with ID " + id + " not found"));
        logger.info("Size with ID " + id + " deleted successfully");
        entityManager.remove(size);
    }
}
