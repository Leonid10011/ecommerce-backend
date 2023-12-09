package com.lbcoding.ecommerce.service.inerfaces;

import com.lbcoding.ecommerce.dto.request.ProductsRequestDTO;
import jakarta.ws.rs.core.Response;

public interface IProductService {
    Response create(ProductsRequestDTO productsRequestDTO);
    Response getByName(String name);
    Response getById(long id);
    Response getAll(int page, int pageSize);
}
