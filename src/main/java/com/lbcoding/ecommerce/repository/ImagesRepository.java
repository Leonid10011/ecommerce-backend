package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.dto.ImagesDTO;
import com.lbcoding.ecommerce.exception.ResourceNotFoundException;
import com.lbcoding.ecommerce.model.Images;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ImagesRepository {
    private static final Logger logger = LoggerFactory.getLogger(InventoryRepository.class);
    @Inject
    EntityManager entityManager;

    public void create(Images image) {
      if(image == null){
          throw new IllegalArgumentException("Image cannot be null");
      }
      logger.info("Persisting new image");
      entityManager.persist(image);
      logger.info("Image persisted successfully with ID: " + image.getId());
    }

    public Optional<Images> findById(Long id){
        return Optional.ofNullable(entityManager.find(Images.class, id));
    }

    /**
     * Find image by product id. When not found throw an NotFoundException to help the caller to provide another imageUrl.
     * If multiple images where found, return the first one.
     * @param productId
     * @return Images - returns an image when at least one was found or throws Exception indicating no image exists.
     */
    public Images findByProductId(Long productId){
        logger.debug("Querying for image by productId: " + productId);
        TypedQuery<Images> query = entityManager.createQuery(
                "SELECT i FROM Images i WHERE i.productId = :productId", Images.class
        ).setParameter("productId", productId);
        // Handle possible multiple pictures
        List<Images> results = query.setMaxResults(1).getResultList();
        if(results.isEmpty()) {
            logger.info("No image found for product ID: " + productId);
            throw new NotFoundException("Image not found for product ID: " + productId);
        }
        logger.info("Image found for product ID: " + productId);
        return results.get(0);
    }

    /**
     * Deletes an image by its id. If not found throw an EntityNotFoundException
     * @param id
     */
    public void delete(Long id){
        logger.info("Deleting image with ID: " + id);
        Images image = findById(id).orElseThrow(() -> new EntityNotFoundException("Image with ID " + id + " not found"));
        entityManager.remove(image);
        logger.info("Image with ID: " + id + " deleted successfully");
    }
}