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
    private String email;
    private Long roleId;
    private String password;

    public User(){

    }

    /**
     *
     * @param username
     * @param email
     * @param roleId
     * @param password
     */
    public User(String username, String email, Long roleId, String password) {
        this.username = username;
        this.email = email;
        this.roleId = roleId;
        this.password = password;
    }

    /**
     *
     * @param id
     * @param username
     * @param email
     * @param roleId
     * @param password
     */
    public User(Long id, String username, String email, Long roleId, String password) {
        this.id = id;
        this.username = username;
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
        return new UserDTO(
            getUsername(),
            getEmail(),
            getRoleId(),
            getPassword()
        );
    }
}
