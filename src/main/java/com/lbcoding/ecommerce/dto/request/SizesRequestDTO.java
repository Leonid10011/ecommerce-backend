package com.lbcoding.ecommerce.dto.request;

public class SizesRequestDTO {
    private long id;
    private String description;

    public SizesRequestDTO() {
    }

    public SizesRequestDTO(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
