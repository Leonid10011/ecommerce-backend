package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.model.Size;
import com.lbcoding.ecommerce.repository.interfaces.ISizeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SizesRepository implements ISizeRepository {
    private static final Logger logger = LoggerFactory.getLogger(InventoryRepository.class);
    @Inject
    EntityManager entityManager;
    /**
     * Creates a new Sizes entity and persists it in the database. This method ensures the uniqueness of the size description;
     * if a size with the given description already exists, it throws a NonUniqueResultException.
     * It validates that the provided SizesRequestDTO is not null before attempting the persistence operation.
     *
     * @param size The data transfer object (DTO) representing the size to be created.
     * @throws IllegalArgumentException If the provided sizeDTO is null.
     * @throws NonUniqueResultException If a size with the same description already exists in the database.
     */
    @Transactional
    public Size create(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }

        logger.info("Persisting new size");

        if (doesSizeExist(size.getName())) {
            logger.warn("Size already exists with description: " + size.getName());
            throw new NonUniqueResultException("Size already exists with description: " + size.getName());
        }

        Size newSize = new Size();
        newSize.setName(size.getName());
        entityManager.persist(newSize);
        entityManager.flush();
        logger.info("Size persisted successfully with ID: " + newSize.getSize_id());
        return newSize;
    }


    /**
     * Retrieves all Sizes objects. If none exist returns an empty list.
     * @return List of Sizes or an empty list.
     */
    public List<Size> findAll(){
        TypedQuery<Size> query = entityManager.createQuery(
                "SELECT s FROM Sizes s", Size.class
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
    public Optional<Size> findById(long id){
        return Optional.ofNullable(entityManager.find(Size.class, id));
    }
    /**
     * Finds a size by description.
     * @param name
     * @return Sizes object if successful. If nothing was found returns an empty Optional object.
     * @throws NoResultException
     */
    @Transactional
    public Optional<Size> findByName(String name){
        logger.info("Querying for size by description");
        TypedQuery<Size> query = entityManager.createQuery(
                "SELECT s FROM Size s WHERE s.name = :name", Size.class
        ).setParameter("name", name);

        try {
            Size result = query.getSingleResult();
            logger.info("Size found for description: " + name);
            return Optional.of(result);
        } catch( NoResultException e ) {
            logger.info("No size found for description: " + name);
            return Optional.empty();
        }
    }

    /**
     * Deletes a Sizes object by its ID;
     * if a size with the given id already exists, it throws a EntityNotFoundException.
     * @param id
     */
    @Transactional
    public void delete(long id){
        logger.info("Deleting size with ID: " + id);
        Size size = findById(id).orElseThrow(() ->new EntityNotFoundException("Size with ID " + id + " not found"));
        logger.info("Size with ID " + id + " deleted successfully");
        entityManager.remove(size);
    }

    /** Updates an existing size entity int the database with the provided information
     * @param size The size entity that holds the new information
     * @throws NotFoundException When no size was found to be updated
     */
    @Override
    @Transactional
    public void update(Size size) {
        logger.info("Finding size with ID: " + size.getSize_id());
        Size updateSize = entityManager.find(Size.class, size.getSize_id());
        if(updateSize != null){
            updateSize.setName(size.getName());
            entityManager.merge(updateSize);
        } else {
            throw new NotFoundException("Size not found with ID: " + size.getSize_id());
        }
    }

    /**
     * @param product
     * @param sizes
     */
    @Override
    public void setSizesForProduct(Product product, String[] sizes) {
        logger.info("Setting sizes for prodcut with ID: " + product.getProduct_id());
        Arrays.stream(sizes).forEach(size -> {
            Optional<Size> foundSize = findByName(size);
            foundSize.ifPresent(value -> product.getSizes().add(value));
        });
        logger.info("Successfully set sizes");
    }

    /**
     * Check for existing Size by description
     */
    private boolean doesSizeExist(String name){
        logger.info("Querying for size by name");
        TypedQuery<Size> query = entityManager.createQuery(
                "SELECT s FROM Size s WHERE s.name = :name", Size.class
        ).setParameter("name", name);
        return !query.getResultList().isEmpty();
    }
}
