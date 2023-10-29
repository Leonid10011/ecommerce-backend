package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.TransactionDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    private Long orderId;
    private Date transactionDate;
    private String paymentMethod;
    private double amount;

    public Transaction(){

    }

    /**
     *
     * @param id
     * @param orderId
     * @param transactionDate
     * @param paymentMethod
     * @param amount
     */
    public Transaction(Long id, Long orderId, Date transactionDate, String paymentMethod, double amount) {
        this.id = id;
        this.orderId = orderId;
        this.transactionDate = transactionDate;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TransactionDTO toDTO(){
        TransactionDTO transactionDTO = new TransactionDTO(
        this.orderId,
        this.transactionDate,
        this.paymentMethod,
        this.amount
        );

        return transactionDTO;
    }
}
