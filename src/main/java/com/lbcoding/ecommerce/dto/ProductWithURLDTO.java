package com.lbcoding.ecommerce.dto;

public class ProductWithURLDTO extends ProductWithQuantityDTO{
    private String imageURL;

    public ProductWithURLDTO(){

    }

    public ProductWithURLDTO(String name, String description, Double price, Long categoryID, int quantity, String imageURL) {
        super(name, description, price, categoryID, quantity);
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
