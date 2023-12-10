/**
 * The User Repository
 */
package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {
    static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Find User by its username
     * @param username
     * @return User or null
     */
    @Transactional
    public Optional<User> findUserByUsername(String username){
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username);

        try{
            return Optional.ofNullable(query.getSingleResult());
        } catch( NoResultException e){
            return Optional.empty();
        }
    }

    /**
     * Find User by id
     * @param id the id of the user
     * @return User
     */
    @Transactional
    public Optional<User> findUserById(Long id) {
        Optional<User> user = Optional.ofNullable(entityManager.find(User.class, id));

        return user.isEmpty() ? Optional.empty() : user;
    }

    /**
     * Create a new User
     * @param user Object containing username, email and password
     */
    @Transactional
    public void createUser(User user){
        if(user == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        if(doesUsernameExist(user.getUsername())){
            throw new EntityExistsException("User with username already exist: " + user.getUsername());
        }
        logger.info("Persisting user");
        User managedUser = entityManager.merge(user);
        entityManager.persist(managedUser);
    }

    /**
     * Delete User by id
     * @param id id of the user to delete
     */
    @Transactional
    public void deleteUser(Long id){
        Optional<User> existingUser = findUserById(id);

        existingUser.ifPresent(entityManager::remove);
    }

    public List<User> getUsers(){
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u", User.class
        );

        return query.getResultList();
    }

    public boolean doesUsernameExist(String name){
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :name", User.class
        ).setParameter("name", name);

        return !query.getResultList().isEmpty();
    }

    public boolean doesUserExist(long id){
        User user = entityManager.find(User.class, id);
        return (user != null);
    }
}
