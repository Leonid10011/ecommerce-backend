package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.dto.ProductImageDTO;
import com.lbcoding.ecommerce.model.ProductImage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.lang.reflect.Type;
import java.util.List;

@ApplicationScoped
public class ProductImageRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void createProductImage(ProductImage productImage){
        entityManager.persist(productImage);
    }

    @Transactional
    public ProductImage get(Long id){
        ProductImage productImage = entityManager.find(ProductImage.class, id);

        return productImage;
    }

    @Transactional
    public List<ProductImage> getProductImages(){
        TypedQuery<ProductImage> query = entityManager.createQuery(
                "SELECT pi FROM ProductImage pi", ProductImage.class);
        List<ProductImage> productImageList = query.getResultList();

        return productImageList;
    }

    @Transactional
    public ProductImage getProductImageByURL(String imageURL){
        TypedQuery<ProductImage> query = entityManager.createQuery(
                "SELECT pi FROM ProductImage pi WHERE pi.imageURL = :imageURL", ProductImage.class
        )
                .setParameter("imageURL", imageURL);
        List<ProductImage> productImageList = query.getResultList();

        if(!productImageList.isEmpty()){
            return productImageList.get(0);
        }
        else {
            return null;
        }
    }

    @Transactional
    public void deleteProductImage(Long id){
        ProductImage productImage = entityManager.find(ProductImage.class, id);

        if(productImage != null){
            entityManager.remove(productImage);
        }
    }
}
