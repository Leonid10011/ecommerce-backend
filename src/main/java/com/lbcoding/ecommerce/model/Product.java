/**
 * Our Products. Base Products consist of name, descr, price and a key valued category.
 * The quantity is stored in Inventory.
 * ImgSrc is stored in ProductImage.
 */

package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.ProductDTO;
import jakarta.persistence.*;

import java.util.List;

/**
 * @params Long id
 * @params String name
 * @params String description
 * @params Double price
 * @params Long categoryID
 */
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "product")
    private List<FavoriteProduct> favoriteProductList;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItemList;

    @OneToOne(mappedBy = "product")
    private ProductImage productImage;
    private String name;

    private String description;

    private Double price;

    private Long categoryId;

    public Product(){

    }

    /**
     *
     * @param id
     * @param name
     * @param description
     * @param price
     * @param categoryID
     */
    public Product(Long id, String name, String description, Double price, Long categoryID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryID;
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
        return categoryId;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryId = categoryID;
    }

    public ProductDTO toDTO(int quantity, String imageURL){
        ProductDTO productDTO = new ProductDTO(
            this.id,
            this.name,
            this.description,
            this.price,
            this.categoryId,
                quantity,
                imageURL
        );

        return productDTO;
    }
}
