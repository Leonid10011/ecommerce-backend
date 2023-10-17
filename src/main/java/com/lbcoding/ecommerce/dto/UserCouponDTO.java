package com.lbcoding.ecommerce.dto;

public class UserCouponDTO {
    private Long userId;
    private Long discountId;

    public UserCouponDTO(){

    }

    public UserCouponDTO(Long userId, Long discountId) {
        this.userId = userId;
        this.discountId = discountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }
}
