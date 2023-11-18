/**
 * Our inventory that holds the quantity of our products.
 * Each product will have an own Inventory entry with its quantiy only for now
 */
package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.model.compositeKey.InventoryId;
import jakarta.persistence.*;

@Entity
@IdClass(InventoryId.class)
@Table(name = "Inventory")
public class Inventory {
    @Id
    private Long productId;
    @Id
    private Long sizeId;

    @Column(name = "quantity")
    private int quantity;

    public Inventory() {
    }

    public Inventory(Long productId, Long sizeId, int quantity) {
        this.productId = productId;
        this.sizeId = sizeId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
