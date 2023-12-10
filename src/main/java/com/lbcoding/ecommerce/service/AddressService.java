package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.AddressDTO;
import com.lbcoding.ecommerce.model.Address;
import com.lbcoding.ecommerce.repository.AddressRepository;
import com.lbcoding.ecommerce.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class AddressService {
    final static Logger logger = LoggerFactory.getLogger(AddressService.class);
    @Inject
    AddressRepository addressRepository;
    @Inject
    UserRepository userRepository;
    @Transactional
    public Response create(AddressDTO addressDTO){
        logger.info("Received request to create address for user ID: " + addressDTO.getUser_id());
        Address address = addressDTOToEntity(addressDTO);
        try {
            addressRepository.createAndSetAddressForUser(addressDTO.getUser_id(), address);
            AddressDTO resDTO = addressEntityToDTO(address, addressDTO.getUser_id());
            logger.info("Successfully created address for user: " + addressDTO.getUser_id());
            return Response.status(Response.Status.CREATED).entity(resDTO).build();
        } catch( IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch ( EntityExistsException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    /**
     * Attempts to find addresses for user ID
     * @param id the id of the user
     * @return Success: set of address with status code 200
     */
    @Transactional
    public Response getByUser(long id) {
        logger.info("Received request to retrieve addresses for user ID: " + id);
        Set<Address> resAddress = new HashSet<Address>();
        userRepository.findUserById(id).ifPresent(user -> {
            resAddress.addAll(user.getAddresses());
        });
        if (resAddress.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity("No addresses found for user ID: " + id).build();
        }
        List<AddressDTO> resDTO = resAddress.stream().map(address -> addressEntityToDTO(address, id)).toList();
        logger.info("Succesfully found addresses for user ID: " + id);
        return Response.status(Response.Status.OK).entity(resDTO).build();
    }

    Address addressDTOToEntity(AddressDTO addressDTO){
        return new Address(
                addressDTO.getAddress_id(),
                addressDTO.getStreet(),
                addressDTO.getCity(),
                addressDTO.getCountry(),
                addressDTO.getZip_code()
        );
    }

    AddressDTO addressEntityToDTO(Address address, long user_id){
        return new AddressDTO(
                address.getAddress_id(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getZip_code(),
                user_id
        );
    }
}
