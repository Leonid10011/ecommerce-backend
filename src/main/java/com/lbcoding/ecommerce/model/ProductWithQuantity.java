package com.lbcoding.ecommerce.model;

public class ProductWithQuantity extends Product{
    private int quantity;

    public ProductWithQuantity() {
    }

    public ProductWithQuantity(Long id, String name, String description, Double price, Long categoryID, int quantity) {
        super(id, name, description, price, categoryID);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
