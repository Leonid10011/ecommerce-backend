package com.lbcoding.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CategoryDTO {
    @NotBlank(message = "Name darf nicht leer sein")
    @Size(max = 20, message = "Name darf maximal 20 Zeichen haben")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Name darf keine Sonderzeichen enthalten")
    private String name;

    public CategoryDTO(){

    }

    public CategoryDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}