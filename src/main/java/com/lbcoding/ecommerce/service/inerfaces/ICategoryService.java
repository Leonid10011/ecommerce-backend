package com.lbcoding.ecommerce.service.inerfaces;

import com.lbcoding.ecommerce.dto.CategoryDTO;
import jakarta.ws.rs.core.Response;

public interface ICategoryService {
    Response create(CategoryDTO categoryDTO);
    Response findAll();
    Response findById(Long id);
    Response findByName(String name);
    Response update(CategoryDTO categoryDTO);
    Response delete(Long id);
}

