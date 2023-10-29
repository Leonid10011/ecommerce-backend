package com.lbcoding.ecommerce.dto;

public class UserDTO {
    private String username;
    private String email;
    private Long roleId;
    private String password;

    public UserDTO(){

    }

    /**
     *
     * @param username
     * @param email
     * @param roleId
     * @param password
     */
    public UserDTO(String username, String email, Long roleId, String password) {
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
}
