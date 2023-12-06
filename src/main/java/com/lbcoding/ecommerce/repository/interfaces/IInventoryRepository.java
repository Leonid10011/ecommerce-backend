package com.lbcoding.ecommerce.repository.interfaces;

import com.lbcoding.ecommerce.dto.InventoryDTO;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.model.Product;
import com.lbcoding.ecommerce.model.compositeKey.InventoryId;

import java.util.List;
import java.util.Optional;

public interface IInventoryRepository {
    Inventory create(InventoryDTO inventoryDTO);
    List<Inventory> getByProduct(long product_id);
    Optional<Inventory> getByProductAndSize(long product_id, long size_id);
    Optional<Inventory> update(InventoryDTO inventoryDTO);
    void delete(InventoryId inventory_id);
    void setInventoryForProduct(Product product, int quantity);
}
