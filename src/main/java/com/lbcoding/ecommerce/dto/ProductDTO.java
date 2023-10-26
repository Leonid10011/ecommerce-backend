package com.lbcoding.ecommerce.dto;

import com.lbcoding.ecommerce.model.Product;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductDTO {
    private Long id;
    @NotBlank(message = "Name darf nicht leer sein")
    @Size(max = 30, message = "Name darf maximal 30 Zeichen haben")
    private String name;

    @Size(max = 100, message = "Beschreibung darf maximal 100 Zeichen lang sein")
    private String description;

    @Digits(integer = 10, fraction = 2)
    private Double price;

    private Long categoryID;

    public ProductDTO(){

    }

    public ProductDTO(Long id, String name, String description, Double price, Long categoryID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryID = categoryID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }
}
