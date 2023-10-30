package com.lbcoding.ecommerce.model;

import jakarta.persistence.*;

@Entity
public class FavoriteProduct {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    private Product product;

    private Long userId;
    private Long productId;

    public FavoriteProduct(){

    }

    /**
     *
     * @param userId
     * @param productId
     */
    public FavoriteProduct(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    /**
     *
     * @param id
     * @param userId
     * @param productId
     */
    public FavoriteProduct(Long id, Long userId, Long productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}

