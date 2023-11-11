package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.UserDTO;
import com.lbcoding.ecommerce.dto.UserProfileDTO;
import com.lbcoding.ecommerce.dto.response.UserResponseDTO;
import com.lbcoding.ecommerce.model.Address;
import com.lbcoding.ecommerce.model.Role;
import com.lbcoding.ecommerce.model.User;
import com.lbcoding.ecommerce.model.UserProfile;
import com.lbcoding.ecommerce.repository.AddressRespository;
import com.lbcoding.ecommerce.repository.RoleRepository;
import com.lbcoding.ecommerce.repository.UserProfileRepository;
import com.lbcoding.ecommerce.repository.UserRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import io.quarkus.elytron.security.common.BcryptUtil;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    RoleRepository  roleRepository;

    @Inject
    AddressRespository addressRespository;

    @Inject
    UserProfileRepository userProfileRepository;

    @Context
    UriInfo uriInfo;
    @Transactional
    public Response createUser(UserDTO userDTO){
        Optional<User> existingUser = userRepository.findUserByUsername(userDTO.getUsername());

        if(existingUser.isPresent()){
            return Response.status(Response.Status.CONFLICT).entity("Username already exists.").build();
        }

        Optional<Role> userRole = roleRepository.findByName("User");

        User user = createUserFromUserDTO(userDTO, userRole.get());

        userRepository.createUser(user);

        UserResponseDTO userResponseDTO = createResponseDTOFromUser(user);

        // URI
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(userResponseDTO.getId()));

        return Response.created(builder.build()).entity(userResponseDTO).build();
    }

    public User createUserFromUserDTO(UserDTO userDTO, Role role){
        return new User(
            userDTO.getUsername(),
            userDTO.getEmail(),
            (role != null) ? role.getId() : 0L,
            BcryptUtil.bcryptHash(userDTO.getPassword())
        );
    }

    public UserResponseDTO createResponseDTOFromUser(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoleId()
        );
    }

    public Response deleteUser(Long id) {
        Optional<User> user = userRepository.findUserById(id);
        if(user.isPresent()){
            System.out.println("Is Present");
            userRepository.deleteUser(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found.").build();
        }
    }

    /**
     * Get all Users
     * @return
     */
    public Response getUser(){
        List<User> userList = userRepository.getUsers();

        if (userList!=null){
            List<UserDTO> userDTOList = userList.stream()
                    .map(User::toDTO)
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(userDTOList).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Get User By Name
     * @param username
     * @return
     */
    public Response getUser(String username){
        Optional<User> user = userRepository.findUserByUsername(username);

        if(user.isPresent()){
            UserDTO userDTO = user.get().toDTO();
            return Response.status(Response.Status.OK).entity(userDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    public Response createProfile(UserProfileDTO userProfileDTO) {

        Set<String> errorMessages = DTOValidator.validateDTO(userProfileDTO);

        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        Optional<Address> address = addressRespository.findById(userProfileDTO.getAddressId());

        if(address.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity("No Address was provided").build();
        }

        Optional<UserProfile> existingUserProfile = userProfileRepository.findById(userProfileDTO.getUserId());

        if(existingUserProfile.isPresent()){
            return Response.status(Response.Status.CONFLICT).build();
        }

        UserProfile userProfile = createUserProfile(userProfileDTO, address.get());

        userProfileRepository.create(userProfile);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.build(Long.toString(userProfile.getId()));

        return Response.created(builder.build()).entity(userProfile).build();
    }

    public Response getUserProfile(Long userId){
        Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userId);

        if(userProfile.isPresent())
            return Response.status(Response.Status.OK).entity(userProfile).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Response deleteUserProfile(Long profileId){
        Optional<UserProfile> userProfile = userProfileRepository.findById(profileId);
        if(userProfile.isPresent()){
            userProfileRepository.delete(profileId);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
    public UserProfile createUserProfile(UserProfileDTO userProfileDTO, Address address){
        return new UserProfile(
                userProfileDTO.getUserId(),
                address.getId(),
                userProfileDTO.getForename(),
                userProfileDTO.getSurname(),
                userProfileDTO.getBirthday()
        );
    }
}
