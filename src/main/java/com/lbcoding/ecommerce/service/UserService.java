package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.UserDTO;
import com.lbcoding.ecommerce.model.User;
import com.lbcoding.ecommerce.repository.UserRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
@ApplicationScoped
public class UserService {
    final static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Inject
    UserRepository userRepository;
    @Transactional
    public Response create(UserDTO userDTO){
        Set<String> errorMessage = DTOValidator.validateDTO(userDTO);
        if(!errorMessage.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        logger.info("Received request to create new user with username: " + userDTO.getUsername());

        User newUser = userDTOToEntity(userDTO);
        userRepository.createUser(newUser);
        UserDTO resDTO = userEntityToDTO(newUser);
        return Response.status(Response.Status.CREATED).entity(resDTO).build();
    }

    User userDTOToEntity(UserDTO userDTO){
        return new User(
                userDTO.getUser_id(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                BcryptUtil.bcryptHash(userDTO.getPassword())
        );
    }

    UserDTO userEntityToDTO(User user){
        return new UserDTO(
                user.getUser_id(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
