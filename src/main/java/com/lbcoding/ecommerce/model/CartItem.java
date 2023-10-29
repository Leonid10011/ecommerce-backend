package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.CartItemDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class CartItem {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long productId;

    private int quantity;

    public CartItem(){

    }

    public CartItem(Long userId, Long productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    /**
     *
     * @param id
     * @param userId
     * @param productId
     * @param quantity
     */
    public CartItem(Long id, Long userId, Long productId, int quantity) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
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


    public CartItemDTO toDTO(){
        CartItemDTO cartItemDTO = new CartItemDTO(
                this.userId,
                this.productId,
                this.quantity
        );
        return cartItemDTO;
    }
}
