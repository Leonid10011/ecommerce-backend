package com.lbcoding.ecommerce.dto;

public class UserDTO {
    private String username;
    private String forename;
    private String surname;
    private String city;
    private String country;
    private String street;
    private String zipCode;
    private String phone;
    private String email;
    private Long roleId;
    private String password;

    public UserDTO(){

    }

    public UserDTO(String username, String forename, String surname, String city, String country, String street, String zipCode, String phone, String email, Long roleId, String password) {
        this.username = username;
        this.forename = forename;
        this.surname = surname;
        this.city = city;
        this.country = country;
        this.street = street;
        this.zipCode = zipCode;
        this.phone = phone;
        this.email = email;
        this.roleId = roleId;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
