package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.RatingDTO;
import com.lbcoding.ecommerce.model.Rating;
import com.lbcoding.ecommerce.repository.RatingRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class RatingService {
    @Inject
    RatingRepository ratingRepository;

    @Inject
    UriInfo uriInfo;

    public Response create(RatingDTO ratingDTO){
        Set<String> errorMessages = DTOValidator.validateDTO(ratingDTO);

        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        Rating existingRating = ratingRepository.get(ratingDTO.getUserId(), ratingDTO.getProductId());

        if(existingRating != null){
            return Response.status(Response.Status.CONFLICT).entity("A Rating from this user for this product already exists").build();
        }

        Rating rating = new Rating();
        rating.setRating(ratingDTO.getRating());
        rating.setText(ratingDTO.getText());
        rating.setDate(ratingDTO.getDate());
        rating.setUserId(ratingDTO.getUserId());
        rating.setProductId(ratingDTO.getProductId());

        ratingRepository.create(rating);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(rating.getId()));

        return Response.created(builder.build()).entity(rating).build();
    }

    public Response getByUser(Long id){
        List<Rating> ratingList = ratingRepository.getByUser(id);

        if(!ratingList.isEmpty()){
            List<RatingDTO> ratingDTOList = ratingList.stream()
                    .map(rating -> rating.toDTO())
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(ratingDTOList).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response getByProduct(Long id){
        List<Rating> ratingList = ratingRepository.getByProduct(id);

        if(!ratingList.isEmpty()){
            List<RatingDTO> ratingDTOList = ratingList.stream()
                    .map(rating -> rating.toDTO())
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(ratingDTOList).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response delete(Long id){
        Rating rating = ratingRepository.get(id);
        if(rating != null){
            ratingRepository.delete(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
