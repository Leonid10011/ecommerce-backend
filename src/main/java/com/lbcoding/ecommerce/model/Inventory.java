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

    private Long productID;

    public Inventory(){

    }

    public Inventory(Long id, int quantity, Long productID) {
        this.id = id;
        this.quantity = quantity;
        this.productID = productID;
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

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }
}
