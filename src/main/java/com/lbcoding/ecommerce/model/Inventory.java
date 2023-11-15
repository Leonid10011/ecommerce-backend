/**
 * Our inventory that holds the quantity of our products.
 * Each product will have an own Inventory entry with its quantiy only for now
 */
package com.lbcoding.ecommerce.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Inventory")
public class Inventory {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "quantity")
    private int quantity;

    private Long productId;

    public Inventory(){

    }

    public Inventory(int quantity, Long productId) {
        this.quantity = quantity;
        this.productId = productId;
    }

    public Inventory(Long id, int quantity, Long productId) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productID) {
        this.productId = productId;
    }
}
