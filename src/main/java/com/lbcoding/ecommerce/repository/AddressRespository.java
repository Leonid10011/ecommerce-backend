package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Address;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.lang.reflect.Type;

@ApplicationScoped
public class AddressRespository {
    @Inject
    EntityManager entityManager;

    @Transactional
    public void create(Address address){
        entityManager.persist(address);
    }

    @Transactional
    public Address get(Long id){
        Address address = entityManager.find(Address.class, id);
        if(address != null)
            return address;
        else
            return null;
    }

    @Transactional
    public void delete(Long id){
        Address address = entityManager.find(Address.class, id);
        if(address != null)
            entityManager.remove(address);
    }
}
