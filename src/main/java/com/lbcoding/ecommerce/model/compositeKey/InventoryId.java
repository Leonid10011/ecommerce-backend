package com.lbcoding.ecommerce.model.compositeKey;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
public class InventoryId implements Serializable {
    private long product_id;
    private long size_id;

    public InventoryId() {
    }

    public InventoryId(long product_id, long size_id) {
        this.product_id = product_id;
        this.size_id = size_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getSize_id() {
        return size_id;
    }

    public void setSize_id(long size_id) {
        this.size_id = size_id;
    }
}
