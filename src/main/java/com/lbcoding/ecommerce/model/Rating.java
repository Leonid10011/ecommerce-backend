package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.RatingDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    private Long id;
    private int rating;
    private String text;
    private Date date;
    private Long userId;
    private Long productId;

    public Rating(){

    }

    public Rating(Long id, int rating, String text, Date date, Long userId, Long productId) {
        this.id = id;
        this.rating = rating;
        this.text = text;
        this.date = date;
        this.userId = userId;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public RatingDTO toDTO(){
        RatingDTO ratingDTO = new RatingDTO(
                this.rating,
                this.text,
                this.date,
                this.userId,
                this.productId
        );
        return ratingDTO;
    }
}
