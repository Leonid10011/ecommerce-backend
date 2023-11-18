package com.lbcoding.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ImagesDTO {
    private long id;
    @NotNull(message = "productId cannot be null")
    private long productId;
    @NotBlank(message = "imageUrl cannot be empty")
    private String imageUrl;

    public ImagesDTO() {
    }

    public ImagesDTO(long id, long productId, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
