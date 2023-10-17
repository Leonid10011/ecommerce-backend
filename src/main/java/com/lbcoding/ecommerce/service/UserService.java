package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.UserDTO;
import com.lbcoding.ecommerce.model.Role;
import com.lbcoding.ecommerce.model.User;
import com.lbcoding.ecommerce.repository.RoleRepository;
import com.lbcoding.ecommerce.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    RoleRepository  roleRepository;

    @Context
    UriInfo uriInfo;

    public Response createUser(UserDTO userDTO){
        User existingUser = userRepository.findUserByUsername(userDTO.getUsername());

        if(existingUser != null){
            return Response.status(Response.Status.CONFLICT).entity("Username already exists.").build();
        }

        Role userRole = roleRepository.get("User");

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setForename(userDTO.getForename());
        user.setSurname(userDTO.getSurname());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        if(userRole != null)
            user.setRoleId(userRole.getId());
        else
            user.setRoleId(0L);

        userRepository.createUser(user);

        // URI
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(user.getId()));

        return Response.created(builder.build()).entity(user).build();
    }

    public Response deleteUser(Long id) {
        if(userRepository.findUserById(id) != null){
            userRepository.deleteUser(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found.").build();
        }
    }

    public Response getUser(){
        List<User> userList = userRepository.getUsers();

        if (userList!=null){
            List<UserDTO> userDTOList = userList.stream()
                    .map(user -> user.toDTO())
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(userDTOList).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Response getUser(String username){
        User user = userRepository.findUserByUsername(username);

        if(user != null){
            UserDTO userDTO = user.toDTO();

            return Response.status(Response.Status.OK).entity(userDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
