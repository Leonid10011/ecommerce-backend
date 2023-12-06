package com.lbcoding.ecommerce.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "size")
public class Size {
    @Id
    @GeneratedValue
    private long size_id;
    private String name;
    @ManyToMany(mappedBy = "sizes")
    private Set<Product> products = new HashSet<>();
    @OneToMany(mappedBy = "size")
    private Set<Inventory> inventories;
    public Size() {
    }

    public Size(long size_id, String name) {
        this.size_id = size_id;
        this.name = name;
    }

    public long getSize_id() {
        return size_id;
    }

    public void setSize_id(Long size_id) {
        this.size_id = size_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
