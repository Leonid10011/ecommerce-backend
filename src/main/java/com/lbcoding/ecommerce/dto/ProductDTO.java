package com.lbcoding.ecommerce.dto;

import jakarta.validation.constraints.*;

/**
 * @params Long id
 * @params String name
 * @params String description
 * @params Double price
 * @params Long categoryID
 * @params int quantity
 * @params String imgURL
 */
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 30, message = "Name can have a maximum of 30 characters")
    private String name;

    @Size(max = 100, message = "Description can have a maximum of 100 characters")
    private String description;

    @Digits(integer = 10, fraction = 2, message = "Invalid price")
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double price;

    private Long categoryID;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private int quantity;
    // Need to adjust when project is finished with image urls
    //@URL(message = "Invalid URL")
    private String imgURL;

    public ProductDTO() {

    }

    /**
     *
     * @param name
     * @param description
     * @param price
     * @param categoryID
     * @param quantity
     * @param imgURL
     */
    public ProductDTO(String name, String description, Double price, Long categoryID, int quantity,  String imgURL) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryID = categoryID;
        this.quantity = quantity;
        this.imgURL = imgURL;
    }

    /**
     *
     * @param id
     * @param name
     * @param description
     * @param price
     * @param categoryID
     * @param quantity
     * @param imgURL
     */
    public ProductDTO(Long id, String name, String description, Double price, Long categoryID, int quantity, String imgURL) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryID = categoryID;
        this.quantity = quantity;
        this.imgURL = imgURL;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
