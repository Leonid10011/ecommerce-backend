package com.lbcoding.ecommerce.dto;

import java.util.Date;

public class OrderDTO {
    long order_id;
    long user_id;
    Date creation_date;
    String status;

    public OrderDTO() {
    }

    public OrderDTO(long order_id, long user_id, Date creation_date, String status) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.creation_date = creation_date;
        this.status = status;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
