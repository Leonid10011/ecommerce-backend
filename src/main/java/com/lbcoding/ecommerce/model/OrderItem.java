package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.model.compositeKey.OrderItemId;
import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @EmbeddedId
    OrderItemId orderItem_id;
    String size_name;
    int quantity;
    double subtotal;

    public OrderItem() {
    }

    public OrderItem(OrderItemId orderItem_id, String size_name, int quantity, double subtotal) {
        this.orderItem_id = orderItem_id;
        this.size_name = size_name;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public OrderItemId getOrderItem_id() {
        return orderItem_id;
    }

    public void setOrderItem_id(OrderItemId orderItem_id) {
        this.orderItem_id = orderItem_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }
}
