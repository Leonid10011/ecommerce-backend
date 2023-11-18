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
    private Long product_id;
    @Id
    private Long size_id;
    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name= "size")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    public Inventory() {
    }

    public Inventory(Long product_id, Long size_id, int quantity) {
        this.product_id = product_id;
        this.size_id = size_id;
        this.quantity = quantity;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getSize_id() {
        return size_id;
    }

    public void setSize_id(Long size_id) {
        this.size_id = size_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
