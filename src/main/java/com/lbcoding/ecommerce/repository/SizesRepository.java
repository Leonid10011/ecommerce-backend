package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.dto.request.SizesRequestDTO;
import com.lbcoding.ecommerce.model.Sizes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SizesRepository {
    private static final Logger logger = LoggerFactory.getLogger(InventoryRepository.class);
    @Inject
    EntityManager entityManager;

    /**
     * Creates a new Sizes entity and persists it in the database. This method ensures the uniqueness of the size description;
     * if a size with the given description already exists, it throws a NonUniqueResultException.
     * It validates that the provided SizesRequestDTO is not null before attempting the persistence operation.
     *
     * @param sizeDTO The data transfer object (DTO) representing the size to be created.
     * @throws IllegalArgumentException If the provided sizeDTO is null.
     * @throws NonUniqueResultException If a size with the same description already exists in the database.
     */
    @Transactional
    public void create(SizesRequestDTO sizeDTO){
        if(sizeDTO == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }
        logger.info("Persisting new size");
        if(doesSizeExists(sizeDTO.getDescription())){
            logger.warn("Size already exists with description: " + sizeDTO.getDescription());
            throw new NonUniqueResultException("Size already exists with description: " + sizeDTO.getDescription());
        }
        Sizes newSize = new Sizes();
        newSize.setDescription(sizeDTO.getDescription());
        entityManager.persist(newSize);
        logger.info("Size persisted successfully with ID: " + newSize.getId());
    }

    /**
     * Retrieves all Sizes objects. If none exist returns an empty list.
     * @return List of Sizes or an empty list.
     */
    public List<Sizes> findAll(){
        TypedQuery<Sizes> query = entityManager.createQuery(
                "SELECT s FROM Sizes s", Sizes.class
        );

        return query.getResultList();
    }

    /**
     * Finds a size by ID. If size not available returns an empty Optional object.
     * @param id
     * @return Sizes Object on success.
     *  If size by name does not exist returns an empty Optional object.
     */
    @Transactional
    public Optional<Sizes> findById(Long id){
        return Optional.ofNullable(entityManager.find(Sizes.class, id));
    }

    /**
     * Finds a size by description.
     * @param description
     * @return Sizes object if successful. If nothing was found returns an empty Optional object.
     */
    @Transactional
    public Optional<Sizes> findByDescription(String description){
        logger.info("Querying for size by description");
        TypedQuery<Sizes> query = entityManager.createQuery(
                "SELECT s FROM Sizes s WHERE s.description = :description", Sizes.class
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

    /**
     * Deletes a Sizes object by its ID;
     * if a size with the given id already exists, it throws a EntityNotFoundException.
     * @param id
     */
    @Transactional
    public void delete(Long id){
        logger.info("Deleting size with ID: " + id);
        Sizes size = findById(id).orElseThrow(() ->new EntityNotFoundException("Size with ID " + id + " not found"));
        logger.info("Size with ID " + id + " deleted successfully");
        entityManager.remove(size);
    }

    /**
     * Check for existing Size by description
     */
    private boolean doesSizeExists(String description){
        logger.info("Querying for size by description");
        TypedQuery<Sizes> query = entityManager.createQuery(
                "SELECT s FROM Sizes s WHERE s.description = :description", Sizes.class
        ).setParameter("description", description);
        if(query.getResultList().isEmpty())
            return false;
        else
            return true;
    }
}
