package com.lbcoding.ecommerce.model;

import jakarta.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue
    private long image_id;
    private long product_id;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "product_id", insertable=false, updatable=false)
    private Product product;
    public Image() {
    }

    public Image(long image_id, long product_id, String imageUrl) {
        this.image_id = image_id;
        this.product_id = product_id;
        this.imageUrl = imageUrl;
    }

    public long getImage_id() {
        return image_id;
    }

    public void setImage_id(long image_id) {
        this.image_id = image_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
