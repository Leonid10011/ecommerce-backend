package com.lbcoding.ecommerce.service.inerfaces;

import com.lbcoding.ecommerce.dto.InventoryDTO;
import jakarta.ws.rs.core.Response;

public interface IInventoryService {
    Response create(InventoryDTO inventoryDTO);
    Response findById(long id);
    Response findByProductAndSize(long product_id, long size_id);
    Response findByProduct(long product_id);
    Response update(InventoryDTO inventoryDTO);
    Response delete(long id);
}
