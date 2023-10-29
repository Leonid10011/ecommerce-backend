/**
 * Here we store the img sources for our products
 */
package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.dto.ProductImageDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ProductImage {
    @Id
    @GeneratedValue
    private Long id;
    private String imageURL;
    private Long productID;

    public ProductImage(){

    }

    /**
     *
     * @param id
     * @param imageURL
     * @param productID
     */
    public ProductImage(Long id, String imageURL, Long productID) {
        this.id = id;
        this.imageURL = imageURL;
        this.productID = productID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public ProductImageDTO toDTO(){
        ProductImageDTO productImageDTO = new ProductImageDTO(
                this.productID,
                this.imageURL
        );

        return  productImageDTO;
    }
}
