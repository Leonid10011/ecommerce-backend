package com.lbcoding.ecommerce.repository.interfaces;

import com.lbcoding.ecommerce.model.Category;
import com.lbcoding.ecommerce.model.Product;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepository {
    void create(Category category);
    Optional<Category> findById(Long id);
    List<Category> findAll();
    Optional<Category> findByName(String name);
    void delete(Long id);
    void update(Category category);
    void setCategoryForProduct(Product product, String categories);
}

