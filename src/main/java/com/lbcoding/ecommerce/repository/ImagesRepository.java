package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Category;
import com.lbcoding.ecommerce.model.Image;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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

    /**
     * Persists an image with the provided product_id and url to the database. If an entry with that product_id and url already exists, throws an NonUniqueResultException.
     * @param image An Image entity
     */
    public void create(Image image) {
      if(image == null){
          throw new IllegalArgumentException("Image cannot be null");
      }
      logger.info("Persisting new image");
      if(doesImageWithProductAndUrlExists(image)){
          String errorMessage = "Image for product_id " + image.getProduct_id() +  " and url " + image.getImageUrl() + " already exists";
          throw new NonUniqueResultException(errorMessage);
      }
      entityManager.persist(image);
      logger.info("Image persisted successfully with ID: " + image.getImage_id());
    }

    /**
     * Retrieves an image by its id.
     * @param id
     * @return
     */
    public Optional<Image> findById(Long id){
        return Optional.ofNullable(entityManager.find(Image.class, id));
    }

    /**
     * Find image by product id. When not found throw an NotFoundException to help the caller to provide another imageUrl.
     * If multiple images where found, return the first one.
     * @param productId
     * @return Images - returns an image when at least one was found or throws Exception indicating no image exists.
     */
    public List<Image> findByProductId(Long productId){
        logger.debug("Querying for image by productId: " + productId);
        TypedQuery<Image> query = entityManager.createQuery(
                "SELECT i FROM Images i WHERE i.productId = :productId", Image.class
        ).setParameter("productId", productId);
        // Handle possible multiple pictures
        List<Image> results = query.getResultList();
        logger.info("Image found for product ID: " + productId);
        return results;
    }
    /**
     * Deletes an image by its id. If not found throw an EntityNotFoundException
     * @param id
     * @throws EntityNotFoundException when image with give id was not found.
     */
    public void delete(Long id){
        logger.info("Deleting image with ID: " + id);
        Image image = findById(id).orElseThrow(() -> new EntityNotFoundException("Image with ID " + id + " not found"));
        entityManager.remove(image);
        logger.info("Image with ID: " + id + " deleted successfully");
    }

    /**
     * Updates an existing image with the given image data.
     * @param image An Image entity which holds the new image values
     * @throws NotFoundException when the image with the given id does not exist
     */
    @Transactional
    public void update(Image image){
        Image updatedImage = entityManager.find(Image.class, image.getImage_id());
        if(updatedImage != null){
            updatedImage.setProduct_id(image.getProduct_id());
            updatedImage.setImageUrl(image.getImageUrl());
            entityManager.merge(updatedImage);
        } else {
            throw new NotFoundException("Category not found with ID: " + image.getImage_id());
        }
    }

    private boolean doesImageWithProductAndUrlExists(Image image){
        TypedQuery<Image> query = entityManager.createQuery(
                "SELECT i FROM image i WHERE i.product_id =:product_id AND i.url =:url", Image.class
        ).setParameter("product_id", image.getProduct_id())
                .setParameter("url", image.getImageUrl());

        return !query.getResultList().isEmpty();
    }
}
