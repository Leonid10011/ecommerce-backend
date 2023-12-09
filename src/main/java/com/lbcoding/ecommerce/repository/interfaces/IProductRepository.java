package com.lbcoding.ecommerce.repository.interfaces;

import com.lbcoding.ecommerce.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductRepository {
    Product create(Product product);
    List<Product> findAll(int page, int pageSize);
    Optional<Product> findByName(String name);
    Optional<Product> findById(long id);
    Product update(Product product);
    void delete(long id);
}


