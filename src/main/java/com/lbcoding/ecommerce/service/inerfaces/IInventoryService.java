package com.lbcoding.ecommerce.service.inerfaces;

import com.lbcoding.ecommerce.dto.InventoryDTO;
import jakarta.ws.rs.core.Response;

public interface IInventoryService {
    Response create(InventoryDTO inventoryDTO);
}
