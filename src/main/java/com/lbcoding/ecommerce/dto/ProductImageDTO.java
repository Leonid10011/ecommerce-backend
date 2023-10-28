package com.lbcoding.ecommerce.dto;

import com.lbcoding.ecommerce.model.ProductImage;
import io.smallrye.common.constraint.NotNull;

/**
 * @params Long productID
 * @params String imageURL
 */
public class ProductImageDTO {
    @NotNull
    private Long productID;
    @NotNull
    private String imageURL;

    public ProductImageDTO(){

    }

    public ProductImageDTO(Long productID, String imageURL) {
        this.productID = productID;
        this.imageURL = imageURL;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
