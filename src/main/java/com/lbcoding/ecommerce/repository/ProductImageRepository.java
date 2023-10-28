package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.ProductImage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductImageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createProductImage(ProductImage productImage) {
        entityManager.persist(productImage);
    }

    @Transactional
    public Optional<ProductImage> findById(Long id) {
        return Optional.ofNullable(entityManager.find(ProductImage.class, id));
    }

    @Transactional
    public List<ProductImage> getProductImages() {
        TypedQuery<ProductImage> query = entityManager.createQuery("SELECT pi FROM ProductImage pi", ProductImage.class);
        return query.getResultList();
    }

    @Transactional
    public Optional<ProductImage> getProductImageByProduct(Long productId) {
        TypedQuery<ProductImage> query = entityManager.createQuery("SELECT pi FROM ProductImage pi WHERE pi.productID = :productId", ProductImage.class);
        query.setParameter("productId", productId);
        Optional<ProductImage> productImage = Optional.ofNullable(query.getSingleResult());

        return !productImage.isPresent() ? null : productImage;
    }

    @Transactional
    public Optional<ProductImage> getProductImageByURL(String imageURL) {
        TypedQuery<ProductImage> query = entityManager.createQuery("SELECT pi FROM ProductImage pi WHERE pi.imageURL = :imageURL", ProductImage.class);
        query.setParameter("imageURL", imageURL);
        Optional<ProductImage> productImage = Optional.ofNullable(query.getSingleResult());

        return !productImage.isPresent() ? null : productImage;
    }

    @Transactional
    public void deleteProductImage(Long id) {
        Optional<ProductImage> productImage = findById(id);

        productImage.ifPresent(entityManager::remove);
    }
}
