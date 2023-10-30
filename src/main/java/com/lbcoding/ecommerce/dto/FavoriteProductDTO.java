package com.lbcoding.ecommerce.dto;

public class FavoriteProductDTO {
    private Long userId;
    private Long productId;

    FavoriteProductDTO(){

    }

    public FavoriteProductDTO(Long userId, Long productId) {
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
