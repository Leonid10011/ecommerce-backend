/**
 * Our inventory that holds the quantity of our products.
 * Each product will have an own Inventory entry with its quantiy only for now
 */
package com.lbcoding.ecommerce.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue
    private long inventory_id;
    private long product_id;
    private long size_id;
    private int quantity;
    private String location;
    @ManyToOne
    @JoinColumn(name = "size_id", insertable = false, updatable = false, nullable = true)
    private Size size;
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false, nullable = true)
    private Product product;

    public Inventory() {
    }

    public Inventory(long inventory_id, long product_id, long size_id, int quantity, String location) {
        this.inventory_id = inventory_id;
        this.product_id = product_id;
        this.size_id = size_id;
        this.quantity = quantity;
        this.location = location;
    }

    public long getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(long inventory_id) {
        this.inventory_id = inventory_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getSize_id() {
        return size_id;
    }

    public void setSize_id(long size_id) {
        this.size_id = size_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
