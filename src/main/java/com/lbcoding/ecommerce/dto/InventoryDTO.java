package com.lbcoding.ecommerce.dto;

import io.smallrye.common.constraint.NotNull;

public class InventoryDTO {

    @NotNull
    private int quantity;

    @NotNull
    private Long productID;

    public InventoryDTO(){

    }

    public InventoryDTO(int quantity, Long productID) {
        this.quantity = quantity;
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }
}
