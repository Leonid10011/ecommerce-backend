package com.lbcoding.ecommerce.dto.response;

public class OrderItemResponseDTO {
    private Long orderItemId;
    private Long orderId;
    private Long productId;
    private String productName;
    private String productDescription;
    private Long categoryId;
    private String imageURL;
    private int orderedQuantity;
    private String orderedPrice;

    public OrderItemResponseDTO(){

    }

    public OrderItemResponseDTO(Long orderItemId, Long orderId, Long productId, String productName, String productDescription, Long categoryId, String imageURL, int orderedQuantity, String orderedPrice) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.categoryId = categoryId;
        this.imageURL = imageURL;
        this.orderedQuantity = orderedQuantity;
        this.orderedPrice = orderedPrice;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public String getOrderedPrice() {
        return orderedPrice;
    }

    public void setOrderedPrice(String orderedPrice) {
        this.orderedPrice = orderedPrice;
    }
}
