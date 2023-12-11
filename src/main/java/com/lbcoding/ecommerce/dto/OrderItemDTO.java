package com.lbcoding.ecommerce.dto;

public class OrderItemDTO {
    long order_id;
    long product_id;
    String size_name;
    int quantity;
    double subtotal;

    public OrderItemDTO() {
    }

    public OrderItemDTO(long order_id, long product_id, String size_name, int quantity, double subtotal) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.size_name = size_name;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
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
