package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.dto.OrderItemDTO;
import com.lbcoding.ecommerce.dto.response.OrderItemResponseDTO;
import com.lbcoding.ecommerce.model.Inventory;
import com.lbcoding.ecommerce.model.OrderItem;
import com.lbcoding.ecommerce.model.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrderItemRepository {
    @Inject
    private EntityManager entityManager;

    @Transactional
    public void create(OrderItem orderItem) {
        entityManager.persist(orderItem);
    }

    @Transactional
    public void delete(Long id) {
        OrderItem orderItem = entityManager.find(OrderItem.class, id);
        if (orderItem != null) {
            entityManager.remove(orderItem);
        }
    }

    @Transactional
    public List<OrderItem> getAllOrderItems() {
        TypedQuery<OrderItem> query = entityManager.createQuery(
                "SELECT oi FROM OrderItem oi", OrderItem.class
        );

        return query.getResultList();
    }

    @Transactional
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        TypedQuery<OrderItem> query = entityManager.createQuery(
                "SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId", OrderItem.class
        ).setParameter("orderId", orderId);

        return query.getResultList();
    }

    @Transactional
    public Optional<OrderItem> getOrderItemById(Long id) {
        OrderItem orderItem = entityManager.find(OrderItem.class, id);
        return Optional.ofNullable(orderItem);
    }

    @Transactional
    public Optional<OrderItem> getOrderItemByProductId(Long productId) {
        TypedQuery<OrderItem> query = entityManager.createQuery(
                "SELECT oi FROM OrderItem oi WHERE oi.productId = :productId", OrderItem.class
        ).setParameter("productId", productId);

        List<OrderItem> orderItemList = query.getResultList();
        return orderItemList.isEmpty() ? Optional.empty() : Optional.of(orderItemList.get(0));
    }

    /**
     * Attempts to get Product and OrderItem joined on productId.
     * @param orderId
     * @return A list of merged object as dto.
     */
    @Transactional
    public List<OrderItemResponseDTO> getOrderItemsWithProduct(Long orderId){
        Query query = entityManager.createQuery(
                "SELECT p.id, oi.id, p.name, p.description, p.categoryId, pi.imageURL, oi.quantity, oi.price " +
                        "FROM Product p " +
                        "INNER JOIN ProductImage pi ON p.id = pi.productId " +
                        "INNER JOIN OrderItem oi ON p.id = oi.productId " +
                        "WHERE oi.orderId = :orderId"
        ).setParameter("orderId", orderId);

        List<Object[]> results = query.getResultList();
        List<OrderItemResponseDTO> dtos = new ArrayList<>();
        for(Object[] result : results){
            OrderItemResponseDTO dto = createOrderItemResponseDTO(result, orderId);
            dtos.add(dto);
        }
        return dtos;
    }

    public OrderItemResponseDTO createOrderItemResponseDTO(Object[] result, Long orderId) {
        Long productId = (Long) result[0];
        Long orderItemId = (Long) result[1];
        String productName = (String) result[2];
        String productDescription = (String) result[3];
        Long categoryId = (Long) result[4];
        String imageURL = (String) result[5];
        Integer orderedQuantity = (Integer) result[6];
        String orderedPrice = result[7].toString();

        return new OrderItemResponseDTO(orderItemId, orderId, productId, productName, productDescription, categoryId, imageURL, orderedQuantity, orderedPrice);
    }

}
