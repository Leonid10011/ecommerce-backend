/**
 * The User Repository
 */
package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import javax.swing.text.html.Option;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {

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

        return Optional.ofNullable(query.getSingleResult());
    }

    /**
     * Find User by id
     * @param id
     * @return User
     */
    @Transactional
    public Optional<User> findUserById(Long id) {
        Optional<User> user = Optional.ofNullable(entityManager.find(User.class, id));

        return user.isEmpty() ? Optional.empty() : user;
    }

    /**
     * Create a new User
     * @param user
     */
    @Transactional
    public void createUser(User user){
        entityManager.persist(user);
    }

    /**
     * Delete User by id
     * @param id
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
}
