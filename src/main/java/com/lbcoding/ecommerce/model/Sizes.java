package com.lbcoding.ecommerce.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Sizes {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    @ManyToMany(mappedBy = "sizes")
    private Set<Products> products = new HashSet<>();

    public Sizes() {
    }

    public Sizes(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Products> getProducts() {
        return products;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }
}
