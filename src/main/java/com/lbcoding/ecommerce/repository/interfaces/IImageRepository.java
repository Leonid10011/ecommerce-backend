package com.lbcoding.ecommerce.repository.interfaces;

import com.lbcoding.ecommerce.model.Image;
import com.lbcoding.ecommerce.model.Product;

import java.util.List;
import java.util.Optional;

public interface IImageRepository {
    void create(Image image);
    Optional<Image> findById(long id);
    List<Image> findByProductId(long product_id);
    void delete(long id);
    void update(Image image);
    void setImagesForProduct(Product product, String[] url);
}
