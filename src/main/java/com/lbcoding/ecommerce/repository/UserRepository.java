/**
 * The User Repository
 */
package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.lang.reflect.Type;
import java.util.List;

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
    public User findUserByUsername(String username){
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username);
        List<User> user = query.getResultList();

        if(!user.isEmpty()){
            return user.get(0);
        } else {
            return null;
        }
    }

    /**
     * Find User by id
     * @param id
     * @return User
     */
    @Transactional
    public User findUserById(Long id) {
        User user = entityManager.find(User.class, id);

        return user;
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
        User existingUser = entityManager.find(User.class, id);

        if(existingUser != null)
            entityManager.remove(existingUser);
    }
    public List<User> getUsers(){
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u", User.class
        );

        List<User>  userList =  query.getResultList();

        return userList;
    }
}
