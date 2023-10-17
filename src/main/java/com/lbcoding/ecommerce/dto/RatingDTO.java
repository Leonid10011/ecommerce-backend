package com.lbcoding.ecommerce.dto;

import io.smallrye.common.constraint.NotNull;

import java.util.Date;

public class RatingDTO {
    @NotNull
    private int rating;
    private String text;
    @NotNull
    private Date date;
    private Long userId;
    private Long productId;

    public RatingDTO(){

    }

    public RatingDTO(int rating, String text, Date date, Long userId, Long productId) {
        this.rating = rating;
        this.text = text;
        this.date = date;
        this.userId = userId;
        this.productId = productId;
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
}
