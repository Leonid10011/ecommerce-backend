package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String username;
    private String forename;
    private String surname;
    private String address;
    private String phone;
    private String email;
    private Long roleId;
    private String password;

    public User(){

    }

    public User(Long id, String username, String forename, String surname, String address, String phone, String email, Long roleId, String password) {
        this.id = id;
        this.username = username;
        this.forename = forename;
        this.surname = surname;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    //To DTO Konversion
    public UserDTO toDTO(){
        UserDTO userDTO = new UserDTO(
                this.username,
                this.forename,
                this.surname,
                this.address,
                this.phone,
                this.email,
                this.roleId,
                this.password
        );

        return userDTO;
    }
}
