package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.request.ProductsRequestDTO;
import com.lbcoding.ecommerce.repository.ProductsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ProductsService {
    private static final Logger logger = LoggerFactory.getLogger(ProductsService.class);
    @Inject
    ProductsRepository productsRepository;
    @Transactional
    public Response create(ProductsRequestDTO productDTO){
        try {
            productsRepository.insert(productDTO);
            logger.info("Service");
            return Response.status(Response.Status.CREATED).build();
        } catch( NotFoundException e) {
            logger.warn("Could not insert product. " + e);
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch ( Exception e) {
            logger.warn("Critical exception");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
