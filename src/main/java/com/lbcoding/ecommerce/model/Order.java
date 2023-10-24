package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.OrderDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Date date;
    private String status;

    public Order(){

    }

    public Order(Long id, Long userId, Date date, String status) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.status = status;
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderDTO toDTO(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(getUserId());
        orderDTO.setOrderDate(getOrderDate());
        orderDTO.setStatus(getStatus());

        return orderDTO;
    }
}
