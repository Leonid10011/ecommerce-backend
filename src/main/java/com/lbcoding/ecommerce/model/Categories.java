package com.lbcoding.ecommerce.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Categories for our products
 */
@Entity
public class Categories {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    @OneToMany(mappedBy = "category")
    private Set<Products> products = new HashSet<>();

    public Categories(){

    }

    /**
     *
     * @param name
     */
    public Categories(String name) {
        this.name = name;
    }

    /**
     *
     * @param id
     * @param name
     */
    public Categories(Long id, String name) {
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

    public Set<Products> getProducts() {
        return products;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }
}
