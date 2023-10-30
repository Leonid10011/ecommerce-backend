package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.dto.RoleDTO;
import com.lbcoding.ecommerce.model.Role;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RoleRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public List<Role> get() {
        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r", Role.class
        );

        return query.getResultList();
    }

    @Transactional
    public Optional<Role> findById(Long id){
        Optional<Role> role = Optional.ofNullable(entityManager.find(Role.class, id));

        return role.isEmpty() ? Optional.empty() : role;
    }

    @Transactional
    public Optional<Role> findByName(String name){
        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r WHERE r.name = :name", Role.class
                )
                .setParameter("name", name);

        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch ( NoResultException e)
        {
            return Optional.empty();
        }
    }

    @Transactional
    public void create(Role role){
        entityManager.persist(role);
    }

    @Transactional
    public void delete(Long id){
        Optional<Role> role = findById(id);

        if(role.isPresent()){
            entityManager.remove(role);
        }
    }
}
