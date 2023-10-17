package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.dto.RoleDTO;
import com.lbcoding.ecommerce.model.Role;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class RoleRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public List<Role> get() {
        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r", Role.class
        );

        List<Role> roleList = query.getResultList();

        return roleList;
    }

    @Transactional
    public Role get(Long id){
        Role role = entityManager.find(Role.class, id);

        return role;
    }

    @Transactional
    public Role get(String name){
        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r WHERE r.name = :name", Role.class
                )
                .setParameter("name", name);

        List<Role> roleList = query.getResultList();

        if(!roleList.isEmpty()){
            return roleList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public void create(Role role){
        entityManager.persist(role);
    }

    @Transactional
    public void delete(Long id){
        Role role = entityManager.find(Role.class, id);

        if(role != null){
            entityManager.remove(role);
        }
    }
}
