package com.lbcoding.ecommerce.dto;

import jakarta.validation.constraints.*;
public class CartItemDTO {
    @NotNull(message = "userId cannot be null.")
    private Long userId;
    @NotNull(message = "productId cannot be null")
    private Long productId;
    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private int quantity;

    public CartItemDTO(){

    }

    /**
     *
     * @param userId
     * @param productId
     * @param quantity
     */
    public CartItemDTO(Long userId, Long productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
