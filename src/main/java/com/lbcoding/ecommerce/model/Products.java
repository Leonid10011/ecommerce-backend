package com.lbcoding.ecommerce.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Products {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Categories category;

    @OneToMany(mappedBy = "product")
    private Set<Images> images;
    public Products() {
    }

    @ManyToMany
    @JoinTable(
            name = "ProductSizes",
            joinColumns = @JoinColumn(name = "categoryId"),
            inverseJoinColumns = @JoinColumn(name = "productId")
    )
    private Set<Sizes> sizes = new HashSet<>();

    public Products(long id, String name, String description, double price, long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Set<Sizes> getSizes() {
        return sizes;
    }

    public void setSizes(Set<Sizes> sizes) {
        this.sizes = sizes;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Images> getImages() {
        return images;
    }

    public void setImages(Set<Images> images) {
        this.images = images;
    }
}

