package com.lbcoding.ecommerce.dto.response;

public class ProductsResponseDTO {
    private long productId;
    private String name;
    private double price;
    private String[] sizes;
    private String imageUrl;
    private  double ratingValue;
    private double category;

    public ProductsResponseDTO() {
    }

    /**
     * Create an initial ProductDTO with all atributes set.
     * @param productId
     * @param name
     * @param price
     * @param sizes
     * @param imageUrl
     * @param ratingValue
     * @param category
     */
    public ProductsResponseDTO(long productId, String name, double price, String[] sizes, String imageUrl, double ratingValue, double category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.sizes = sizes;
        this.imageUrl = imageUrl;
        this.ratingValue = ratingValue;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String[] getSizes() {
        return sizes;
    }

    public void setSizes(String[] sizes) {
        this.sizes = sizes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(double ratingValue) {
        this.ratingValue = ratingValue;
    }

    public double getCategory() {
        return category;
    }

    public void setCategory(double category) {
        this.category = category;
    }
}
