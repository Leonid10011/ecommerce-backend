package com.lbcoding.ecommerce.dto.request;
import io.smallrye.common.constraint.NotNull;

import java.util.Date;

public class RatingRequestDTO {
    private Long id;
    @NotNull
    private int rating;
    private String text;
    @NotNull
    private Date date;
    private Long userId;
    private Long productId;

    public RatingRequestDTO(){

    }

    /**
     *
     * @param id
     * @param rating
     * @param text
     * @param date
     * @param userId
     * @param productId
     */
    public RatingRequestDTO(Long id, int rating, String text, Date date, Long userId, Long productId) {
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
}
