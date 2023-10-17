package com.lbcoding.ecommerce.dto;

import java.util.Date;

public class OrderDTO {
    private Long userId;
    private Date date;
    private String Status;

    public OrderDTO(){

    }

    public OrderDTO(Long userId, Date orderDate, String status) {
        this.userId = userId;
        this.date = orderDate;
        Status = status;
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
