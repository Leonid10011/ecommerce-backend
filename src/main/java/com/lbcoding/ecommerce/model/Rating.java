package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.model.compositeKey.RatingId;
import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "rating")
public class Rating {
    @EmbeddedId
    private RatingId rating_id;
    private int rating_value;
    private String rating_text;
    private Date creation_date;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id" , referencedColumnName = "product_id",insertable=false, updatable=false)
    private Product product;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",insertable=false, updatable=false)
    private User user;
    public Rating(){

    }

    public Rating(RatingId rating_id, int rating_value, String rating_text, Date creation_date) {
        this.rating_id = rating_id;
        this.rating_value = rating_value;
        this.rating_text = rating_text;
        this.creation_date = creation_date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RatingId getRating_id() {
        return rating_id;
    }

    public void setRating_id(RatingId rating_id) {
        this.rating_id = rating_id;
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
