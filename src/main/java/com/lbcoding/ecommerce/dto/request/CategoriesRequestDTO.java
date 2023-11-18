package com.lbcoding.ecommerce.dto.request;

public class CategoriesRequestDTO {
    private long  id;
    private String name;

    public CategoriesRequestDTO() {
    }

    public CategoriesRequestDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
