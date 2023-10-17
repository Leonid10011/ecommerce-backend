package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.CartItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

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

        List<CartItem> cartItemList = query.getResultList();

        if(!cartItemList.isEmpty())
            return cartItemList;
        else
            return null;
    }

    @Transactional
    public CartItem get(Long id){
        CartItem cartItem = entityManager.find(CartItem.class, id);

        return cartItem;
    }

    @Transactional
    public CartItem getByProduct(Long id){
        TypedQuery<CartItem> query = entityManager.createQuery(
                "SELECT c FROM CartItem c WHERE c.productId = :id", CartItem.class
        ). setParameter("id", id);

        List<CartItem> cartItemList = query.getResultList();

        if(!cartItemList.isEmpty()){
            return cartItemList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public CartItem getByUser(Long id){
        TypedQuery<CartItem> query = entityManager.createQuery(
                "SELECT c FROM CartItem c WHERE c.userId = :id", CartItem.class
        ). setParameter("id", id);

        List<CartItem> cartItemList = query.getResultList();

        if(!cartItemList.isEmpty()){
            return cartItemList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(Long id){
        CartItem cartItem = entityManager.find(CartItem.class, id);

        if(cartItem != null){
            entityManager.remove(cartItem);
        }
    }
}
