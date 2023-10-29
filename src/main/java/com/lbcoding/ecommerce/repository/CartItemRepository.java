package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.CartItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CartItemRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void create(CartItem cartItem){
        entityManager.persist(cartItem);
    }

    @Transactional
    public List<CartItem> get(){
        TypedQuery<CartItem> query = entityManager.createQuery(
                "SELECT c FROM CartItem c", CartItem.class
        );

        return query.getResultList();
    }
    @Transactional
    public void update(Long cartItemId, int quantity){
        Optional<CartItem> cartItem = findById(cartItemId);

        if(cartItem.isPresent()){
            cartItem.get().setQuantity(cartItem.get().getQuantity() + quantity);
            entityManager.merge(cartItem);
            entityManager.getTransaction().commit();
        }
    }
    @Transactional
    public Optional<CartItem> findById(Long id){
        return Optional.ofNullable(entityManager.find(CartItem.class, id));
    }

    @Transactional
    public List<CartItem> getByProduct(Long id){
        TypedQuery<CartItem> query = entityManager.createQuery(
                "SELECT c FROM CartItem c WHERE c.productId = :id", CartItem.class
        ). setParameter("id", id);

        return query.getResultList();
    }

    @Transactional
    public List<CartItem> getByUser(Long id){
        TypedQuery<CartItem> query = entityManager.createQuery(
                "SELECT c FROM CartItem c WHERE c.userId = :id", CartItem.class
        ). setParameter("id", id);

        return query.getResultList();
    }
    public Optional<CartItem> findByUserAndProduct(Long userId, Long productId){
        TypedQuery<CartItem> query = entityManager.createQuery(
                "SELECT ci FROM CartItem ci WHERE ci.userId = :userId AND ci.productId = :productId", CartItem.class
        ).setParameter("userId", userId)
                .setParameter("productId", productId);

        try{

            return Optional.ofNullable(query.getSingleResult());
        } catch( NoResultException e){
            return Optional.empty();
        }

    }
    @Transactional
    public void delete(Long id){
        Optional<CartItem> cartItem = findById(id);
        cartItem.ifPresent(entityManager::remove);
    }
}
