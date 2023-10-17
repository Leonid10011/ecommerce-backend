package com.lbcoding.ecommerce.model;

import com.lbcoding.ecommerce.dto.RoleDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Role(){

    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleDTO toDTO(){
        RoleDTO roleDTO = new RoleDTO(
                this.name
        );

        return roleDTO;
    }
}
