package com.lbcoding.ecommerce.dto;



import java.util.Date;

public class UserProfileDTO {

    private Long Id;
    private Long userId;
    private Long addressId;
    private String forename;
    private String surname;
    private Date birthday;

    public UserProfileDTO(){

    }

    public UserProfileDTO(Long userId, Long addressId, String forename, String surname, Date birthday) {
        this.userId = userId;
        this.addressId = addressId;
        this.forename = forename;
        this.surname = surname;
        this.birthday = birthday;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
