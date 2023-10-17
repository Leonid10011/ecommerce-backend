package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.DiscountDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Discount {
    @Id
    @GeneratedValue
    private Long id;
    private String code;
    private String description;
    private String discountType;
    private double value;
    private Date startDate;
    private Date endDate;

    public Discount(){

    }

    public Discount(Long id, String code, String description, String discountType, double value, Date startDate, Date endDate) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discountType = discountType;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DiscountDTO toDTO(){
        DiscountDTO discountDTO = new DiscountDTO(
        code,
        description,
        discountType,
        value,
        startDate,
        endDate
        );

        return discountDTO;
    }
}
