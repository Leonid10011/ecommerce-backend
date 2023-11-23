package com.lbcoding.ecommerce.dto;

public class ImageDTO {
    private long image_id;
    private long product_id;
    private String url;

    public ImageDTO() {
    }

    public ImageDTO(long image_id, long product_id, String url) {
        this.image_id = image_id;
        this.product_id = product_id;
        this.url = url;
    }

    public long getImage_id() {
        return image_id;
    }

    public void setImage_id(long image_id) {
        this.image_id = image_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
