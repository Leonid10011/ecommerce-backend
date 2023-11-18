package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.request.CategoriesRequestDTO;
import com.lbcoding.ecommerce.model.Categories;
import com.lbcoding.ecommerce.repository.CategoriesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NonUniqueResultException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@ApplicationScoped
public class CategoriesService {
    public final static Logger logger = LoggerFactory.getLogger(CategoriesService.class);
    @Inject
    CategoriesRepository categoryRepository;

    public Response create(CategoriesRequestDTO categoryDTO){
        logger.info("Received request create Categories");
        try {
            Categories category = new Categories();
            category.setName(categoryDTO.getName());
            categoryRepository.create(categoryDTO);
            return Response.status(Response.Status.CREATED).entity("created").build();
        } catch ( NonUniqueResultException e) {
            return Response.status(Response.Status.CONFLICT).entity("Already exists").build();
        }
    }
}
