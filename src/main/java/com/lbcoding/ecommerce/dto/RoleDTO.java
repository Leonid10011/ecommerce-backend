package com.lbcoding.ecommerce.dto;

public class RoleDTO {
    private String name;

    public RoleDTO(){

    }

    /**
     *
     * @param name
     */
    public RoleDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
