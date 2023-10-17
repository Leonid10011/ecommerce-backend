/**
 * Our Products. Base Products consist of name, descr, price and a key valued category.
 * The quantity is stored in Inventory.
 * ImgSrc is stored in ProductImage.
 */

package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.dto.ProductImageDTO;
import com.lbcoding.ecommerce.dto.ProductWithURLDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.ws.rs.core.Response;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Double price;

    private Long categoryID;

    public Product(){

    }

    public Product(Long id, String name, String description, Double price, Long categoryID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryID = categoryID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public ProductDTO toDTO(){
        ProductDTO productDTO = new ProductDTO(
        name,
        description,
        price,
        categoryID
        );

        return productDTO;
    }

    public ProductWithURLDTO toImageDTO(int quantity, String imageURL ){
        ProductWithURLDTO productWithURLDTO = new ProductWithURLDTO(
                this.name,
                this.description,
                this.price,
                this.categoryID,
                quantity,
                imageURL
        );

        return productWithURLDTO;
    }
}
