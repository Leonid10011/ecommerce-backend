package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.OrderItemDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    private Long orderId;

    private Long productId;

    private Integer quantity;

    private Double price;

    public OrderItem(){

    }

    public OrderItem(Long id, Long orderId, Long productId, Integer quantity, Double price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OrderItemDTO toDTO(){
        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setOrderId(getOrderId());
        orderItemDTO.setProductId(getProductId());
        orderItemDTO.setQuantity(getQuantity());
        orderItemDTO.setPrice(getPrice());

        return orderItemDTO;
    }
}