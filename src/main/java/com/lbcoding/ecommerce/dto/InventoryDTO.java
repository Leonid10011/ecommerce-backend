package com.lbcoding.ecommerce.dto;

public class InventoryDTO {
    long inventory_id;
    long product_id;
    long size_id;
    int quantity;
    String location;

    public InventoryDTO() {
    }

    public InventoryDTO(long inventory_id, long product_id, long size_id, int quantity, String location) {
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
}
