package com.lbcoding.ecommerce.model.compositeKey;

import java.io.Serializable;

public class InventoryId implements Serializable {
    private Long product_id;
    private Long size_id;

    public InventoryId() {
    }

    public InventoryId(Long product_id, Long size_id) {
        this.product_id = product_id;
        this.size_id = size_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getSize_id() {
        return size_id;
    }

    public void setSize_id(Long size_id) {
        this.size_id = size_id;
    }
}
