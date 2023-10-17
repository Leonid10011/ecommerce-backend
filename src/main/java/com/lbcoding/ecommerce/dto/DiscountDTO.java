package com.lbcoding.ecommerce.dto;

import java.util.Date;

public class DiscountDTO {
    private String code;
    private String description;
    private String discountType;
    private double value;
    private Date startDate;
    private Date endDate;

    public DiscountDTO(){

    }

    public DiscountDTO(String code, String description, String discountType, double value, Date startDate, Date endDate) {
        this.code = code;
        this.description = description;
        this.discountType = discountType;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
