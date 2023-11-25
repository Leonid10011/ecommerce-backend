package com.lbcoding.ecommerce.service.inerfaces;

import com.lbcoding.ecommerce.dto.ImageDTO;
import jakarta.ws.rs.core.Response;

public interface IImagesService {
    Response create(ImageDTO imageDTO);
    Response getByProduct(long product_id);
}
