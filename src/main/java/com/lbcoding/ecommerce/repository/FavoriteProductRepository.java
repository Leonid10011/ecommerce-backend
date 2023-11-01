package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.FavoriteProduct;
import com.lbcoding.ecommerce.model.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FavoriteProductRepository {
    @Inject
    private EntityManager entityManager;

    @Transactional
    public void create(FavoriteProduct favoriteProduct){
        entityManager.persist(favoriteProduct);
    }

    @Transactional
    public Optional<FavoriteProduct> findById(Long id){
        Optional<FavoriteProduct> optionalFavoriteProduct = Optional.ofNullable(entityManager.find(FavoriteProduct.class, id));

        return optionalFavoriteProduct.isEmpty() ? Optional.empty() : optionalFavoriteProduct;
    }

    @Transactional
    public Optional<FavoriteProduct> findByUserAndProduct(Long userId, Long productId){
        TypedQuery<FavoriteProduct> query = entityManager.createQuery(
                "SELECT f FROM FavoriteProduct f WHERE f.userId = :userId AND f.productId = :productId", FavoriteProduct.class
        ).setParameter("userId", userId)
                .setParameter("productId", productId);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch ( NoResultException e){
            return Optional.empty();
        }
    }

    @Transactional
    public List<Product> findByUserId(Long userId){
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT f.product FROM FavoriteProduct f WHERE f.userId = :userId", Product.class
        ).setParameter("userId", userId);

        return query.getResultList();
    }

    @Transactional
    public void delete(Long id){
        Optional<FavoriteProduct> optionalFavoriteProduct = findById(id);

        optionalFavoriteProduct.ifPresent(entityManager::remove);
    }

    @Transactional
    public void deleteByUserAndProduct(Long userId, Long productId){
        TypedQuery<FavoriteProduct> query = entityManager.createQuery(
                "SELECT fp FROM FavoriteProduct fp WHERE fp.userId = :userId AND fp.productId = :productId", FavoriteProduct.class
        ).setParameter("userId", userId).setParameter("productId", productId);

        Optional<FavoriteProduct> optionalFavoriteProduct = Optional.ofNullable(query.getSingleResult());
        optionalFavoriteProduct.ifPresent(entityManager::remove);
    }

}
