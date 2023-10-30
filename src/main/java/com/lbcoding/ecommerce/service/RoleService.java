package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.RoleDTO;
import com.lbcoding.ecommerce.model.Role;
import com.lbcoding.ecommerce.repository.RoleRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoleService {
    @Inject
    RoleRepository roleRepository;

    @Context
    UriInfo uriInfo;

    public Response create(RoleDTO roleDTO ){
        Set<String> errorMessages = DTOValidator.validateDTO(roleDTO);

        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorMessages)
                    .build();
        }

        Optional<Role> existingRole = roleRepository.findByName(roleDTO.getName());

        if(existingRole.isPresent()){
            return Response.status(Response.Status.CONFLICT).entity("Role already exists").build();
        }

        Role role = new Role();
        role.setName(roleDTO.getName());

        roleRepository.create(role);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(role.getId()));

        return Response.created(builder.build()).entity(role).build();
    }

    public Response get(){
        List<Role> roleList = roleRepository.get();

        if(!roleList.isEmpty()){
            roleList.stream()
                    .map(role -> role.toDTO())
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(roleList).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    public Response get(Long id){
        Optional<Role> role = roleRepository.findById(id);
        if(role.isPresent()){
            RoleDTO roleDTO = role.get().toDTO();

            return Response.status(Response.Status.OK).entity(roleDTO).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


}
