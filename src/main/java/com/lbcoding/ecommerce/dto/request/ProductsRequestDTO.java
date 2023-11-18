package com.lbcoding.ecommerce.dto.request;

import jakarta.validation.constraints.*;

public class ProductsRequestDTO {
    private long productId;
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 30, message = "Name can have a maximum of 30 characters")
    private String name;
    @Size(max = 100, message = "Description can have a maximum of 100 characters")
    private String description;
    @Digits(integer = 10, fraction = 2, message = "Invalid price")
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double price;
    private String[] sizes;
    private String[] imageUrl;
    private String category;

    public ProductsRequestDTO() {
    }

    public ProductsRequestDTO(long productId, String name, String description, Double price, String[] sizes, String[] imageUrl, String category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sizes = sizes;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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

    public String[] getSizes() {
        return sizes;
    }

    public void setSizes(String[] sizes) {
        this.sizes = sizes;
    }

    public String[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
