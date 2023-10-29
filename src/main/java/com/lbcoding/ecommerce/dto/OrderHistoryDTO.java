package com.lbcoding.ecommerce.dto;

public class OrderHistoryDTO {
    private Long userId;
    private Long orderId;

    public OrderHistoryDTO(){

    }

    /**
     *
     * @param userId
     * @param orderId
     */
    public OrderHistoryDTO(Long userId, Long orderId) {
        this.userId = userId;
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
