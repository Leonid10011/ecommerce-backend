package com.lbcoding.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Date;
@Entity
public class UserProfile {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long addressId;
    private String forename;
    private String surname;
    private LocalDate birthday;

    public UserProfile(){

    }

    /**
     *
     * @param userId
     * @param addressId
     * @param forename
     * @param surname
     * @param birthday
     */
    public UserProfile(Long userId, Long addressId, String forename, String surname, LocalDate birthday) {
        this.userId = userId;
        this.addressId = addressId;
        this.forename = forename;
        this.surname = surname;
        this.birthday = birthday;
    }

    /**
     *
     * @param id
     * @param userId
     * @param addressId
     * @param forename
     * @param surname
     * @param birthday
     */
    public UserProfile(Long id, Long userId, Long addressId, String forename, String surname, LocalDate birthday) {
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
        this.forename = forename;
        this.surname = surname;
        this.birthday = birthday;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
