package com.lbcoding.ecommerce.model.compositeKey;

import java.io.Serializable;

public class InventoryId implements Serializable {
    private Long productId;
    private Long sizeId;

    public InventoryId() {
    }

    public InventoryId(Long productId, Long sizeId) {
        this.productId = productId;
        this.sizeId = sizeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }
}
