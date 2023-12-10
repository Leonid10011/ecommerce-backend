package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.UserDTO;
import com.lbcoding.ecommerce.model.User;
import com.lbcoding.ecommerce.model.types.authentication.UserValidation;
import com.lbcoding.ecommerce.repository.UserRepository;
import com.lbcoding.ecommerce.security.JwtUtils;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@ApplicationScoped
public class AuthenticationService {
    final static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    @Inject
    UserRepository userRepository;
    /**
     * Attempts to sign in a user
     * @param userDTO user sign in information
     * @return Success: Token with status code 200.
     * Failure: empty String with status code 400.
     */
    public Response login(UserDTO userDTO) {
        logger.info("Received request to sign in user with username: " + userDTO.getUsername());
        UserValidation userValidation = isValidUser(userDTO.getUsername(), userDTO.getPassword());
        if (userValidation.isValid()) {
            String token = generateJwtToken(userDTO.getUsername(), userValidation.getId());
            return Response.status(Response.Status.OK).entity(token).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Could not validate user").build();
        }
    }

    /**
     * Validates a user. I.e. if username and password matches
     * @param username the username
     * @param password the user password
     * @return UserValidation object, that contains valid status and a corresponding id when is valid.
     */
    private UserValidation isValidUser(String username, String password) {
        logger.info("Validating user with username: " + username);
        Optional<User> existingUser = userRepository.findUserByUsername(username);

        if(existingUser.isPresent()){
            logger.info("Successfully validated user: " + username);
            if(BcryptUtil.matches(password, existingUser.get().getPassword())){
                return new UserValidation(
                        true,
                        existingUser.get().getUser_id()
                );
            }
        }

        logger.error("Failed to validate user with username: " + username);
        return new UserValidation(false);
    }

    private String generateJwtToken(String username, Long id) {
        return  generateJwtToken(username, id, false);
    }
    private String generateJwtToken(String username, Long id, boolean admin) {
        Set<String> roles = new HashSet<String>();

        if(admin){
            roles.add("admin");
        }

        roles.add("User");

        return JwtUtils.generateToken(username, roles, id);
    }
}
