/**
 * A DTO for Product With Quantity
 */
package com.lbcoding.ecommerce.dto;

public class ProductWithQuantityDTO extends ProductDTO{
    private int quantity;

    public ProductWithQuantityDTO(){

    }

    public ProductWithQuantityDTO(Long id, String name, String description, Double price, Long categoryID, int quantity) {
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
