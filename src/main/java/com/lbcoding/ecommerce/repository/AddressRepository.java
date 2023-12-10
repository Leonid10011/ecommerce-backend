package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Address;
import com.lbcoding.ecommerce.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AddressRepository {
    final static Logger logger = LoggerFactory.getLogger(AddressRepository.class);
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Attempts to persist an address
     * @param user_id id of the user
     * @param address the address to add
     * @throws IllegalArgumentException if user does not exist
     * @throws EntityExistsException if same address already exist
     */
    @Transactional
    public void createAndSetAddressForUser(long user_id, Address address){
        // check if user exist
        if(!doesUserExist(user_id)){
            logger.error("User must exist");
            throw new IllegalArgumentException("User must exist");
        }
        if(doesAddressExist(address)){
            throw new EntityExistsException("Address already exist");
        }

        User user = entityManager.find(User.class, user_id);
        logger.info("Found user with ID: " + user.getUser_id());
        entityManager.persist(address);
        user.getAddresses().add(address);
        address.getUsers().add(user);
    }
    boolean doesUserExist(long user_id){
        logger.info("Checking if user exist with ID: " + user_id);
        return entityManager.find(User.class, user_id) != null;
    }
    boolean doesAddressExist(Address address){
        logger.info("Checking if address exist");
        TypedQuery<Address> query = entityManager.createQuery(
                "SELECT a FROM Address a WHERE " +
                        "a.street = :street AND " +
                        "a.city = :city AND " +
                        "a.country = :country AND " +
                        "a.zip_code = :zipCode", Address.class

        ).setParameter("street", address.getStreet())
                .setParameter("city", address.getCity())
                .setParameter("country", address.getCountry())
                .setParameter("zipCode", address.getCountry());

        return !query.getResultList().isEmpty();
    }
}
