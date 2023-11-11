package com.lbcoding.ecommerce.dto.response;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private Long roleId;

    public UserResponseDTO(){

    }

    /**
     *
     * @param username
     * @param email
     * @param roleId
     */
    public UserResponseDTO(Long id, String username, String email, Long roleId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
