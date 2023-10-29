package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Address;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.lang.reflect.Type;
import java.util.Optional;

@ApplicationScoped
public class AddressRespository {
    @Inject
    EntityManager entityManager;

    @Transactional
    public void create(Address address){
        entityManager.persist(address);
    }

    @Transactional
    public Optional<Address> findById(Long id){
        Address address = entityManager.find(Address.class, id);
        return Optional.ofNullable(address);
    }

    @Transactional
    public Optional<Address> findByAddress(Address address){
        TypedQuery<Address> query = entityManager.createQuery(
                "SELECT a from Address a WHERE a.city = :city AND a.country = :country AND a.street = :street AND a.zipCode = :zipCode", Address.class
        )
                .setParameter("city", address.getCity())
                .setParameter("country", address.getCountry())
                .setParameter("street", address.getStreet())
                .setParameter("zipCode", address.getZipCode());

        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch ( NoResultException e){
            return Optional.empty();
        }
    }
    @Transactional
    public void delete(Long id){
        Address address = entityManager.find(Address.class, id);
        if(address != null)
            entityManager.remove(address);
    }
}
