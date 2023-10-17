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
}
