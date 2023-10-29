package com.lbcoding.ecommerce.dto;

public class CartItemDTO {
    private Long userId;
    private Long productId;

    public CartItemDTO(){

    }

    /**
     *
     * @param userId
     * @param productId
     */
    public CartItemDTO(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
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
