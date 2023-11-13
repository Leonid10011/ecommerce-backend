/**
 * Here we store the img sources for our products
 */
package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.ProductImageDTO;
import jakarta.persistence.*;

@Entity
public class ProductImage {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    private Product product;
    private String imageURL;
    private Long productId;

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
        this.productId = productID;
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
        return productId;
    }

    public void setProductID(Long productID) {
        this.productId = productID;
    }

    public ProductImageDTO toDTO(){
        ProductImageDTO productImageDTO = new ProductImageDTO(
                this.productId,
                this.imageURL
        );

        return  productImageDTO;
    }
}
