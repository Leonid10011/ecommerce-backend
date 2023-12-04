package com.lbcoding.ecommerce.repository.interfaces;

import com.lbcoding.ecommerce.model.Size;

import java.util.List;
import java.util.Optional;

public interface ISizeRepository {
    Size create(Size size);
    Optional<Size> findById(long id);
    Optional<Size> findByName(String name);
    List<Size> findAll();
    void delete(long id);
    void update(Size size);
}
