package com.lbcoding.ecommerce.dto;

/**
 *
 */
public class UserDTO {
    private long user_id;
    private String username;
    private String email;
    private long role_id;
    private String password;

    public UserDTO(){

    }

    public UserDTO(long user_id, String username, String email, Long roleId, String password) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.role_id = roleId;
        this.password = password;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
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
        return role_id;
    }

    public void setRoleId(Long roleId) {
        this.role_id = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
