package com.lbcoding.ecommerce.dto;

import io.smallrye.common.constraint.NotNull;

/**
 * @params Long productID
 * @params String imageURL
 */
public class    ProductImageDTO {
    @NotNull
    private Long productId;
    @NotNull
    private String imageURL;

    public ProductImageDTO(){

    }

    /**
     *
     * @param productID
     * @param imageURL
     */
    public ProductImageDTO(Long productID, String imageURL) {
        this.productId = productID;
        this.imageURL = imageURL;
    }

    public Long getProductID() {
        return productId;
    }

    public void setProductID(Long productID) {
        this.productId = productID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
