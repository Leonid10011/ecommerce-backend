package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.RatingDTO;
import com.lbcoding.ecommerce.model.Rating;
import com.lbcoding.ecommerce.model.compositeKey.RatingId;
import com.lbcoding.ecommerce.repository.RatingRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@ApplicationScoped
public class RatingService {
    final static Logger logger = LoggerFactory.getLogger(RatingService.class);
    @Inject
    RatingRepository ratingRepository;

    /**
     * Attempts to create a rating for a product
     * @param ratingDTO containg all product_id, user_id, rating_value, rating_text and creation date
     * @return Success: created Rating with status code 201.
     * Failure: validation error message or with status code 400
     *  null argument with status code 400
     *  errorMessage for duplicate entity with status code 409
     */
    @Transactional
    public Response create(RatingDTO ratingDTO){
        Set<String> errorMessage = DTOValidator.validateDTO(ratingDTO);
        if(!errorMessage.isEmpty()){
            logger.warn("Failed to validate Rating");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        Rating rating = ratingDTOToEntity(ratingDTO);
        try {
            ratingRepository.setRatingForProduct(rating);
            RatingDTO resDTO = ratingEntityToDTO(rating);
            return Response.status(Response.Status.CREATED).entity(resDTO).build();
        } catch ( EntityExistsException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch ( IllegalArgumentException e ) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    private Rating ratingDTOToEntity(RatingDTO ratingDTO){
        RatingId ratingId = new RatingId(ratingDTO.getProduct_id(), ratingDTO.getUser_id());
        return new Rating(
                ratingId,
                ratingDTO.getRating_value(),
                ratingDTO.getRating_text(),
                ratingDTO.getCreation_date()
        );
    }

    private RatingDTO ratingEntityToDTO(Rating rating){
        return new RatingDTO(
                rating.getRating_id().getProduct_id(),
                rating.getRating_id().getUser_id(),
                rating.getRating_value(),
                rating.getRating_text(),
                rating.getCreation_date()
        );
    }
}
