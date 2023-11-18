package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.dto.request.CategoriesRequestDTO;
import com.lbcoding.ecommerce.model.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategoriesRepository {
    public static final Logger logger = LoggerFactory.getLogger(CategoriesRepository.class);
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public void create(CategoriesRequestDTO category){
        if(category == null){
            throw new IllegalArgumentException("Category cannot be null");
        }
        logger.info("Persisting new category");
        if(doesCategoryExists(category.getName())){
            throw new NonUniqueResultException("Already exists");
        }
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        entityManager.persist(newCategory);
        logger.info("Category persisted successfully with ID: " + newCategory.getCategory_id());
    }
    public Optional<Category> findById(Long id){
        return Optional.ofNullable(entityManager.find(Category.class, id));
    }

    /**
     * Get all categories if there are any. Otherwise throw an NotFoundException.
     * @return List<Category> - A list containing all categories. If empty, throw an NotFoundException
     */
    @Transactional
    public List<Category> get(){
        logger.info("Querying to get all categories");
        TypedQuery<Category> query = entityManager.createQuery(
                "SELECT c FROM Categories c", Category.class
        );
        List<Category> categories = query.getResultList();
        if(categories.isEmpty()){
            logger.info("No categories found");
            return categories;
        }
        logger.info("Categories found");
        return categories;
    }
    /**
     * Get category by name. If not found throw an NotFoundException.
     * @param name
     * @return
     */
    @Transactional
    public Optional<Category> findByName(String name) {
        logger.info("Querying find category by name " + name);
        TypedQuery<Category> query = entityManager.createQuery(
                "SELECT c FROM Categories c WHERE c.name = :name", Category.class);
        query.setParameter("name", name);

        try {
            Category result = query.getSingleResult();
            logger.info("Category found by name: " + name);
            return Optional.of(result);
        } catch( NoResultException e){
            logger.info("Category not found for name: " + name);
            throw new NotFoundException("Category not found");
        }
    }
    /**
     * Delete category by id
     * @param id
     */
    @Transactional
    public void delete(Long id){
        logger.info("Deleting category by ID: " + id);
        Category category = findById(id).orElseThrow(() -> new NotFoundException("Category with ID " + id + " not found"));
        entityManager.remove(category);
        logger.info("Category deleted successfully");
    }
    /**
     * Helper to find existing
     */
    private boolean doesCategoryExists(String name) {
        logger.info("Querying find category by name " + name);
        TypedQuery<Category> query = entityManager.createQuery(
                "SELECT c FROM Categories c WHERE c.name = :name", Category.class);
        query.setParameter("name", name);

        if(query.getResultList().isEmpty())
            return false;
        else
            return true;
    }
}
