package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.AddressDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String street;
    private String city;
    private String zipCode;
    private String country;

    public Address(){

    }

    /**
     *
     * @param street
     * @param city
     * @param zipCode
     * @param country
     */
    public Address(String street, String city, String zipCode, String country) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    /**
     *
     * @param id
     * @param street
     * @param city
     * @param zipCode
     * @param country
     */
    public Address(Long id, String street, String city, String zipCode, String country) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AddressDTO toDTO(){
        AddressDTO addressDTO = new AddressDTO(
                this.street,
                this.city,
                this.zipCode,
                this.country
        );

        return addressDTO;
    }
}
