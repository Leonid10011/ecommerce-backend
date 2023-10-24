package com.lbcoding.ecommerce.dto;

import java.util.Date;

/**
 * @params Long userId;
 * @params Date date;
 * @params String Status;
 */
public class OrderDTO {
    private Long userId;
    private Date date;
    private String status;

    public OrderDTO(){

    }

    public OrderDTO(Long userId, Date orderDate, String status) {
        this.userId = userId;
        this.date = orderDate;
        this.status = status;
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
