package com.lbcoding.ecommerce.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue
    long address_id;
    String street;
    String city;
    String country;
    String zip_code;

    @ManyToMany
    @JoinTable(
            name = "user_address",
            joinColumns = @JoinColumn(name ="address_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<User> users = new HashSet<>();

    public Address() {
    }

    public Address(long address_id, String street, String city, String country, String zip_code) {
        this.address_id = address_id;
        this.street = street;
        this.city = city;
        this.country = country;
        this.zip_code = zip_code;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
