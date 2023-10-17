package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Find category by ID
     * @param id
     * @return
     */
    public Category findById(Long id){
        return entityManager.find(Category.class, id);
    }

    /**
     * Get all categories
     * @return
     */
    @Transactional
    public List<Category> get(){
        String jpql = "SElECT c FROM Category c";

        TypedQuery<Category> query = entityManager.createQuery(jpql, Category.class);

        List<Category> categories = query.getResultList();

        return categories;
    }

    /**
     * Create new category
     * @param category
     */
    @Transactional
    public void create(Category category){
        entityManager.persist(category);
    }

    /**
     * Delete category by id
     * @param id
     */
    @Transactional
    public void delete(Long id){

        Category category = entityManager.find(Category.class, id);

        if(category != null){
            entityManager.remove(category);
        }
    }

    /**
     * Get category by name
     * @param name
     * @return
     */
    @Transactional
    public Category findByName(String name) {
        TypedQuery<Category> query = entityManager.createQuery(
                "SELECT c FROM Category c WHERE c.name = :name", Category.class);
        query.setParameter("name", name);

        List<Category> resultList = query.getResultList();

        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
}
