package com.lbcoding.ecommerce.service.inerfaces;

import com.lbcoding.ecommerce.dto.SizeDTO;
import jakarta.ws.rs.core.Response;

public interface ISizesService {
    Response create(SizeDTO sizeDTO);
    Response findById(long id);
    Response findByName(String name);
    Response update(SizeDTO sizeDTO);
    Response delete(long id);
}
