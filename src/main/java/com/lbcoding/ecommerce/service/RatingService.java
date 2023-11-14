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
import java.util.Optional;
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
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages.add(("Could not validate!"))).build();
        }

        Optional<Rating> existingRating = ratingRepository.get(ratingDTO.getUserId(), ratingDTO.getProductId());

        if(existingRating.isPresent()){
            return Response.status(Response.Status.CONFLICT).entity("A Rating from this user for this product already exists").build();
        }

        Rating rating = createRatingFromRatingDTO(ratingDTO);

        ratingRepository.create(rating);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(rating.getId()));

        return Response.created(builder.build()).entity(rating).build();
    }

    public  Rating createRatingFromRatingDTO(RatingDTO ratingDTO){
        return new Rating(
                ratingDTO.getId(),
                ratingDTO.getRating(),
                ratingDTO.getText(),
                ratingDTO.getDate(),
                ratingDTO.getUserId(),
                ratingDTO.getProductId()
        );
    }

    public  RatingDTO createRatingDTOFromRating(Rating rating){
        return new RatingDTO(
                rating.getId(),
                rating.getRating(),
                rating.getText(),
                rating.getDate(),
                rating.getUserId(),
                rating.getProductId()
        );
    }

    public Response getByUser(Long id){
        List<Rating> ratingList = ratingRepository.getByUser(id);

        if(!ratingList.isEmpty()){
            List<RatingDTO> ratingDTOList = ratingList.stream()
                    .map(this::createRatingDTOFromRating)
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
                    .map(this::createRatingDTOFromRating)
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(ratingDTOList).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response delete(Long id){
        Optional<Rating> rating = ratingRepository.get(id);
        if(rating.isPresent()){
            ratingRepository.delete(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response updateRatingValue(RatingDTO ratingDTO) {
        Optional<Rating> rating = ratingRepository.get(ratingDTO.getId());

        if (rating.isPresent()) {
            ratingRepository.updateRatingValue(ratingDTO.getId(), ratingDTO.getRating());
            return Response.ok().entity("Rating updated successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Rating not found").build();
        }
    }

    public Response updateRatingText(RatingDTO ratingDTO) {
        Optional<Rating> rating = ratingRepository.get(ratingDTO.getId());

        if (rating.isPresent()) {
            ratingRepository.updateRatingText(ratingDTO.getId(), ratingDTO.getText());
            return Response.ok().entity("Rating text updated successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Rating not found").build();
        }
    }

}
