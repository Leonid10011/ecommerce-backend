package com.lbcoding.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "BuyOrder")
public class BuyOrder {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Date date;
    private String Status;

    public BuyOrder(){

    }

    public BuyOrder(Long id, Long userId, Date date, String status) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        Status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return date;
    }

    public void setOrderDate(Date orderDate) {
        this.date = orderDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
