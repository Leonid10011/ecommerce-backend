package com.lbcoding.ecommerce.dto;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Min;

/**
 * @params int quantity
 * @params Long productID
 */
public class InventoryDTO {

    @NotNull
    @Min(message = "Quantity must be at least 1", value = 1)
    private int quantity;

    @NotNull
    private Long productID;

    public InventoryDTO(){

    }

    /**
     *
     * @param quantity
     * @param productID
     */
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
