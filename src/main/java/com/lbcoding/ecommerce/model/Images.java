package com.lbcoding.ecommerce.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Images {
    @Id
    @GeneratedValue
    private long id;
    private long productId;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "productId", insertable=false, updatable=false)
    private Products product;
    public Images() {
    }

    public Images(long id, long productId, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}
