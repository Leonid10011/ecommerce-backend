package com.lbcoding.ecommerce.model.compositeKey;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RatingId implements Serializable {
    private long product_id;
    private long user_id;

    public RatingId() {
    }

    public RatingId(long product_id, long user_id) {
        this.product_id = product_id;
        this.user_id = user_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
