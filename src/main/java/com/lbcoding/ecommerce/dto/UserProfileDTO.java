package com.lbcoding.ecommerce.dto;



import java.time.LocalDate;

public class UserProfileDTO {

    private Long Id;
    private Long userId;
    private Long addressId;
    private String forename;
    private String surname;
    private LocalDate birthday;

    public UserProfileDTO(){

    }

    /**
     *
     * @param userId
     * @param addressId
     * @param forename
     * @param surname
     * @param birthday
     */
    public UserProfileDTO(Long userId, Long addressId, String forename, String surname, LocalDate birthday) {
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
