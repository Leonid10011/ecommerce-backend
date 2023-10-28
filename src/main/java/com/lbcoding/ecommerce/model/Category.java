package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.CategoryDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Categories for our products
 */
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Category(){

    }

    public Category(String name) {
        this.name = name;
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryDTO toDTO(){
        CategoryDTO categoryDTO = new CategoryDTO(
                this.name
        );

        return categoryDTO;
    }
}
