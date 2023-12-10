package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.UserProfileDTO;
import com.lbcoding.ecommerce.model.UserProfile;
import com.lbcoding.ecommerce.repository.AddressRepository;
import com.lbcoding.ecommerce.repository.UserProfileRepository;
import com.lbcoding.ecommerce.repository.UserRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@ApplicationScoped
public class UserProfileService {
    final static Logger logger = LoggerFactory.getLogger(UserProfileService.class);
    @Inject
    UserProfileRepository userProfileRepository;
    @Inject
    AddressRepository addressRepository;
    @Inject
    UserRepository userRepository;

    @Transactional
    public Response create(UserProfileDTO userProfileDTO){
        Set<String> errorMessages = DTOValidator.validateDTO(userProfileDTO);
        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }
        UserProfile userProfile = createUserProfile(userProfileDTO);
        try {
            userProfileRepository.create(userProfile);
            return Response.status(Response.Status.CREATED).entity(userProfileDTO).build();
        } catch( IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch( EntityExistsException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @Transactional
    public Response getByUser(long user_id){
        logger.info("Received request to retrieve user profile for user ID: " + user_id);
        try {
            UserProfile userProfile = userProfileRepository.getByUser(user_id);
            UserProfileDTO userProfileDTO = userprofileEntityToDTO(userProfile);
            return Response.status(Response.Status.OK).entity(userProfileDTO).build();
        } catch ( NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("No profile found for use ID: " + user_id).build();
        }

    }

    UserProfile createUserProfile(UserProfileDTO userProfileDTO){
        return new UserProfile(
                0L,
                userProfileDTO.getUser_id(),
                userProfileDTO.getFirst_name(),
                userProfileDTO.getLast_name(),
                userProfileDTO.getGender()
        );
    }

    UserProfileDTO userprofileEntityToDTO(UserProfile userProfile){
        return new UserProfileDTO(
                0L,
                userProfile.getUser_id(),
                userProfile.getFirst_name(),
                userProfile.getLast_name(),
                userProfile.getGender()
        );
    }
}
