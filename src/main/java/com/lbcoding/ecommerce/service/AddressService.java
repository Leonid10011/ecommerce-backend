package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.AddressDTO;
import com.lbcoding.ecommerce.model.Address;
import com.lbcoding.ecommerce.repository.AddressRespository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class AddressService {
    @Inject
    private AddressRespository addressRespository;
    @Inject
    UriInfo uriInfo;
    @Transactional
    public Response createAddress(AddressDTO addressDTO){
        Set<String> errorMessages = DTOValidator.validateDTO(addressDTO);
        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        Optional<Address> existingAddress = addressRespository.findByAddress(addressDTOtoAddress(addressDTO));

        if(existingAddress.isPresent())
            // If already existing, return the address to have access to the id for the UserProfile
            return Response.status(Response.Status.CONFLICT).entity(existingAddress).build();

        Address address = addressDTOtoAddress(addressDTO);

        addressRespository.create(address);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.build(Long.toString(address.getId()));

        return Response.created(builder.build()).entity(address).build();
    }

    public Response getAddress(Long addressId){
        Optional<Address> address = addressRespository.findById(addressId);

        return address.isPresent()
                ? Response.status(Response.Status.OK).entity(address).build()
                : Response.status(Response.Status.NOT_FOUND).entity("Address not found").build();
    }

    public Response deleteAddress(Long addressId){
        Optional<Address> address = addressRespository.findById(addressId);
        if(address.isPresent()){
            addressRespository.delete(addressId);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Address Not Found").build();
    }

    private Address addressDTOtoAddress(AddressDTO addressDTO){
        return new Address(
                addressDTO.getCity(),
                addressDTO.getCountry(),
                addressDTO.getStreet(),
                addressDTO.getZipCode()
        );
    }
}
