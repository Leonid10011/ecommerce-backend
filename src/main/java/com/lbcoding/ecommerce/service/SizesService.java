package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.request.SizesRequestDTO;
import com.lbcoding.ecommerce.model.Sizes;
import com.lbcoding.ecommerce.repository.SizesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NonUniqueResultException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class SizesService {
    private final static Logger logger = LoggerFactory.getLogger(SizesService.class);
    @Inject
    private SizesRepository sizesRepository;

    /**
     * Handles the creation of a new size entity based on the provided SizesRequestDTO.
     * It invokes the SizesRepository to persist the new size. If the size already exists
     * (as determined by its unique name), this method responds with a conflict status.
     * Otherwise, it successfully creates the size and returns a created status.
     *
     * @param sizes The SizesRequestDTO containing the details of the size to be created.
     * @return A Response object indicating the result of the create operation. This can be
     *         a success (status 201, "Created size successfully") or a conflict (status 409,
     *         "Size could not be created. Size with same name already exists.") in case of
     *         a duplicate size.
     */
    public Response create(SizesRequestDTO sizes){
        logger.info("Received request create size");
        try {
            sizesRepository.create(sizes);
            logger.info("Created size successfully");
            return Response.status(Response.Status.CREATED).entity("Created size successfully").build();
        } catch( NonUniqueResultException e) {
            logger.warn("Request declined. Size with the same name already exists");
            return Response.status(Response.Status.CONFLICT).entity("Size could not be created. Size with same name already exists.").build();
        }
    }
    /**
     * Retrieves a list of all size entities available in the database.
     * This method queries the SizesRepository to fetch all sizes and
     * returns them in a Response object. The response is always successful
     * with a status of 200 OK, including when the list of sizes is empty.
     *
     * @return A Response object containing the list of all sizes and a status of 200 OK.
     */
    public Response findAll(){
        logger.info("Received request get all sizes");
        List<Sizes> sizes = sizesRepository.findAll();
        logger.info("Successfully retrieved sizes");
        return Response.status(Response.Status.OK).entity(sizes).build();
    }
}
