package com.lbcoding.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Sizes {
    @Id
    @GeneratedValue
    private Long id;
    private String sizeDescription;

    public Sizes() {
    }

    public Sizes(Long id, String sizeDescription) {
        this.id = id;
        this.sizeDescription = sizeDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSizeDescription() {
        return sizeDescription;
    }

    public void setSizeDescription(String sizeDescription) {
        this.sizeDescription = sizeDescription;
    }
}
