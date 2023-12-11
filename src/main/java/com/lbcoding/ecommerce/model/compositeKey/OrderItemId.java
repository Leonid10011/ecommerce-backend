package com.lbcoding.ecommerce.model.compositeKey;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItemId {
    private long order_id;
    private long product_id;

    public OrderItemId() {
    }

    public OrderItemId(long order_id, long product_id) {
        this.order_id = order_id;
        this.product_id = product_id;
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
}
