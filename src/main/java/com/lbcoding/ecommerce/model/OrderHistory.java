package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.OrderHistoryDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class OrderHistory {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long orderId;

    public OrderHistory(){

    }

    public OrderHistory(Long id, Long userId, Long orderId) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public OrderHistoryDTO toDTO(){
        OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO(
                userId,
                orderId
        );

        return orderHistoryDTO;
    }
}
