package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Order;
import com.lbcoding.ecommerce.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class OrderRepository {
    final static Logger logger = LoggerFactory.getLogger(OrderRepository.class);
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Attempts to persist an order
     * @param order contains data to persist
     * @throws IllegalArgumentException when order is null
     */
    @Transactional
    public void create(Order order){
        if(order == null){
            throw new IllegalArgumentException("Order cannot be null");
        }
        if(!doesUserExist(order.getUser_id())){
            throw new NotFoundException("User does not exist with ID: " + order.getUser_id());
        }
        logger.info("Persisting order");
        entityManager.persist(order);
    }

    /**
     * Attempts to retrieve an order by its id
     * @param id id of the order
     * @return the found order
     */
    public Order findById(long id){
        logger.info("Searching for order with ID: " + id);
        Order order = entityManager.find(Order.class, id);
        if(order == null){
            throw new NotFoundException("Order not found with ID: " + id);
        }
        logger.info("Successfully found order with ID: " + id);
        return order;
    }

    /**
     * Attempts to retrieve orders for user
     * @param user_id id of the user
     * @return list of orders or empty list
     */
    public List<Order> findByUser(long user_id){
        logger.info("Searching for order for user ID: " + user_id);
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o WHERE " +
                        "o.user_id = :user_id", Order.class
        ).setParameter("user_id", user_id);
        logger.info("Successfully retrieved orders for user ID: " + user_id);
        return query.getResultList();
    }

    /**
     *  Attempts to update the status text of order
     * @param order the order data containing new status and order ID
     * @throws NotFoundException when order ID is not found
     */
    public void updateStatus(Order order){
        logger.info("Updating status of order");
        Order updatedOrder = entityManager.find(Order.class, order.getOrder_id());
        if(updatedOrder == null){
            throw new NotFoundException("Update order status failed. Order not found with ID: " + order.getOrder_id());
        }
        updatedOrder.setStatus(order.getStatus());
        entityManager.merge(updatedOrder);
    }

    boolean doesUserExist(long user_id){
        logger.info("Checking if user exist with ID: " + user_id);
        return entityManager.find(User.class, user_id) != null;
    }
}
