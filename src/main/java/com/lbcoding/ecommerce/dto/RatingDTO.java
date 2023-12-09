package com.lbcoding.ecommerce.dto;

import java.util.Date;

public class RatingDTO {
    long product_id;
    long user_id;
    int rating_value;
    String rating_text;
    Date creation_date;

    public RatingDTO() {
    }

    public RatingDTO(long product_id, long user_id, int rating_value, String rating_text, Date creation_date) {
        this.product_id = product_id;
        this.user_id = user_id;
        this.rating_value = rating_value;
        this.rating_text = rating_text;
        this.creation_date = creation_date;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getRating_value() {
        return rating_value;
    }

    public void setRating_value(int rating_value) {
        this.rating_value = rating_value;
    }

    public String getRating_text() {
        return rating_text;
    }

    public void setRating_text(String rating_text) {
        this.rating_text = rating_text;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }
}
