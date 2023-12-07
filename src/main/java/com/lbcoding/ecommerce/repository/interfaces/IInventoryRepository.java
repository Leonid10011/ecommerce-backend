package com.lbcoding.ecommerce.repository.interfaces;

import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.model.Product;

import java.util.List;

public interface IInventoryRepository {
    void create(Inventory inventory);
    List<Inventory> findByProduct(long product_id);
    List<Inventory> findByProductAndSize(long product_id, long size_id);
    void update(Inventory inventory);
    void delete(long id);
    void setInventoryForProduct(Product product, int quantity);
}
