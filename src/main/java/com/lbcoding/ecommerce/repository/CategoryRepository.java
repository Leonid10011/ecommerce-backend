package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Find category by ID
     * @param id
     * @return
     */
    public Optional<Category> findById(Long id){

        Optional<Category> category;

        try {
            Category result = entityManager.find(Category.class, id);
            category = Optional.of(result);
        } catch(Exception e){
            category = Optional.empty();
        }

        return category.isEmpty() ? Optional.empty() : category;
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

        return categories.isEmpty() ? null : categories;

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
        Optional<Category> category = findById(id);

        category.ifPresent(entityManager::remove);
    }

    /**
     * Get category by name
     * @param name
     * @return
     */
    @Transactional
    public Optional<Category> findByName(String name) {
        TypedQuery<Category> query = entityManager.createQuery(
                "SELECT c FROM Category c WHERE c.name = :name", Category.class);
        query.setParameter("name", name);

        Optional<Category> category;

        try {
            Category result = query.getSingleResult();
            category = Optional.of(result);
        } catch( Exception e){
            category = Optional.empty();
        }

        return category.isEmpty() ? Optional.empty() : category;
    }
}
