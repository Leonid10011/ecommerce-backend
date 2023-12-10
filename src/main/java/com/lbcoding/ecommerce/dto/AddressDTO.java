package com.lbcoding.ecommerce.dto;

public class AddressDTO {
    long address_id;
    String street;
    String city;
    String country;
    String zip_code;

    long user_id;

    public AddressDTO() {
    }

    public AddressDTO(long address_id, String street, String city, String country, String zip_code, long user_id) {
        this.address_id = address_id;
        this.street = street;
        this.city = city;
        this.country = country;
        this.zip_code = zip_code;
        this.user_id = user_id;
    }

    public long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(long address_id) {
        this.address_id = address_id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
