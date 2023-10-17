package com.lbcoding.ecommerce.dto;

import java.util.Date;

public class TransactionDTO {
    private Long orderId;
    private Date transactionDate;
    private String paymentMethod;
    private double amount;

    public TransactionDTO(){

    }

    public TransactionDTO(Long orderId, Date transactionDate, String paymentMethod, double amount) {
        this.orderId = orderId;
        this.transactionDate = transactionDate;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
