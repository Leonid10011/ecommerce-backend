/*
package com.lbcoding.ecommerce.repository;


import com.lbcoding.ecommerce.dto.ProductDTO;
import com.lbcoding.ecommerce.model.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;

@ApplicationScoped
public class ProductRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public Product getById(Long id){
        Product product = entityManager.find(Product.class, id);

        return product;
    }

    @Transactional
    public Product getProductByName(String name){

        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.name = :name", Product.class);
        query.setParameter("name", name);

        List<Product> resultList= query.getResultList();

        if(!resultList.isEmpty()){
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Product> getProducts(){

        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p", Product.class);

        List<Product> products = query.getResultList();

        return products;
    }

    @Transactional
    public void createProduct(Product product){
        entityManager.persist(product);
    }

    @Transactional
    public void deleteProduct(Long id){
        Product existingProduct = entityManager.find(Product.class, id);

        if(existingProduct != null){
            entityManager.remove(existingProduct);
        }
    }

    public List<Product> getSearchName(String searchTerm){
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p from Product p WHERE p.name LIKE :searchTerm", Product.class
        ).setParameter("searchTerm", "%" + searchTerm + "%");
        System.out.println("TEST"+ query.getResultList());
        List<Product> products = query.getResultList();

        if(!products.isEmpty()){
            return products;
        }
        else return null;
    }
}
*/

package com.lbcoding.ecommerce.repository;

import com.lbcoding.ecommerce.model.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Product.class, id));
    }

    public Optional<Product> findProductByName(String name) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.name = :name", Product.class);
        query.setParameter("name", name);

        List<Product> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    public List<Product> getProducts() {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p", Product.class);

        return query.getResultList();
    }

    @Transactional
    public void createProduct(Product product) {
        entityManager.persist(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Optional<Product> existingProduct = findById(id);
        existingProduct.ifPresent(product -> {
            entityManager.remove(product);
            logger.info("Product with id {} deleted.", id);
        });
    }

    public List<Product> searchProductsByName(String searchTerm) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.name LIKE :searchTerm", Product.class
        ).setParameter("searchTerm", "%" + searchTerm + "%");
        List<Product> products = query.getResultList();
        return products;
    }
}