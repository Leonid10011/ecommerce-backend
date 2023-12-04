package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.SizeDTO;
import com.lbcoding.ecommerce.model.Size;
import com.lbcoding.ecommerce.repository.SizesRepository;
import com.lbcoding.ecommerce.service.inerfaces.ISizesService;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class SizesService implements ISizesService {
    private final static Logger logger = LoggerFactory.getLogger(SizesService.class);
    @Inject
    private SizesRepository sizesRepository;
    @Inject
    EntityManager entityManager;

    /**
     * Handles the creation of a new size entity based on the provided SizesRequestDTO.
     * It invokes the SizesRepository to persist the new size. If the size already exists
     * (as determined by its unique name), this method responds with a conflict status.
     * Otherwise, it successfully creates the size and returns a created status.
     *
     * @param sizeDTO The SizesRequestDTO containing the details of the size to be created.
     * @return A Response object indicating the result of the create operation. This can be
     *         a success (status 201, "Created size successfully") or a conflict (status 409,
     *         "Size could not be created. Size with same name already exists.") in case of
     *         a duplicate size.
     */
    @Transactional
    public Response create(SizeDTO sizeDTO){
        Set<String> errorMessage = DTOValidator.validateDTO(sizeDTO);
        if(!errorMessage.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        Size size = sizeDTOToEntity(sizeDTO);

        logger.info("Received request create size");
        try {
            Size newSize = sizesRepository.create(size);
            SizeDTO resDTO = sizeEntityToDTO(newSize);
            logger.info("Created size successfully with ID: " + resDTO.getSize_id());
            return Response.status(Response.Status.CREATED).entity(resDTO).build();
        } catch( NonUniqueResultException e) {
            logger.warn("Request declined. Size with the same name already exists");
            return Response.status(Response.Status.CONFLICT).entity("Size could not be created. Size with same name already exists.").build();
        }
    }

    /** Attempts to retrieve a Size entity by given ID.
     * @param id The unique identifier of the size entity
     * @return On Success returns a `{SizeDTO}` with status code 200.
     *  On Error returns an errorMessage with status code 404.
     */
    @Override
    public Response findById(long id) {
        logger.info("Received request to find size by ID: " + id);
        Optional<Size> size = sizesRepository.findById(id);
        if(size.isPresent()){
            SizeDTO resSize = sizeEntityToDTO(size.get());
            logger.info("Successfully retrieved size with ID: " + id);
            return Response.status(Response.Status.OK).entity(resSize).build();
        } else {
            String errorMessage = "Failed to find size with ID: " + id;
            logger.warn(errorMessage);
            return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
        }
    }

    /** Attempts to retrieve a Size entity by given NAME.
     * @param name The descriptor of the size
     * @return On Success returns a `{SizeDTO}` with status code 200.
     *  On Error returns an errorMessage with status code 404.
     */
    @Override
    public Response findByName(String name) {
        logger.info("Received request to find size by NAME: " + name);
        Optional<Size> size = sizesRepository.findByName(name);
        if(size.isPresent()){
            SizeDTO resSize = sizeEntityToDTO(size.get());
            logger.info("Successfully retrieved size with NAME: " + name);
            return Response.status(Response.Status.OK).entity(resSize).build();
        } else {
            String errorMessage = "Failed to find size with NAME: " + name;
            logger.warn(errorMessage);
            return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
        }
    }

    /** Attempts to update a DTO in the database by the given DTO information id a matching entity is found with the DTO's id.
     * @param sizeDTO The DTO that holds the new values for a size entity in the database
     * @return On Success returns the updated SizeDTO with status code 200
     *  On Error returns an errorMessage with status code 400 (if validation failes) or status code 404 (if size with id does not exist).
     */
    @Override
    public Response update(SizeDTO sizeDTO) {
        logger.info("REceived request to update DTO with NAME: " + sizeDTO.getName());
        Set<String> errorMessages = DTOValidator.validateDTO(sizeDTO);
        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        Size updatedSize = sizeDTOToEntity(sizeDTO);

        try {
            sizesRepository.update(updatedSize);
            logger.info("SUccessfully updated size with ID: " + sizeDTO.getSize_id());
            return Response.status(Response.Status.OK).entity(updatedSize).build();
        } catch( NotFoundException e) {
            String errorMessage = "Failed to find and update size with ID: " + sizeDTO.getSize_id();
            logger.warn(errorMessage);
            return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
        }
    }

    /** Attempts to delte a size by its id
     * @param id The unique identifier of the size to be deleted
     * @return On success returns a success message with error code 204.
     *  On Failure returns an error message with error code 404.
     */
    @Override
    public Response delete(long id) {
        logger.info("Received request to delete size with ID: " + id);
        try {
            sizesRepository.delete(id);
            String errorMessage = "Successfully deleted size with ID: " + id;
            logger.info(errorMessage);
            return Response.noContent().entity(errorMessage).build();
        } catch ( EntityNotFoundException e ) {
            String errorMessage =  "Failed to find size wiht ID: " + id;
            logger.warn(errorMessage);
            return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
        }
    }

    /**
     * Retrieves a list of all size entities available in the database.
     * This method queries the SizesRepository to fetch all sizes and
     * returns them in a Response object. The response is always successful
     * with a status of 200 OK, including when the list of sizes is empty.
     *
     * @return A Response object containing the list of all sizes and a status of 200 OK.
     */
    public Response findAll(){
        logger.info("Received request get all sizes");
        List<Size> sizes = sizesRepository.findAll();
        List<SizeDTO> resSizes = sizes.stream().map(
                this::sizeEntityToDTO
        ).toList();
        logger.info("Successfully retrieved sizes");
        return Response.status(Response.Status.OK).entity(resSizes).build();
    }

    private SizeDTO sizeEntityToDTO(Size size){
        return new SizeDTO(
                size.getSize_id(),
                size.getName()
        );
    }

    private Size sizeDTOToEntity(SizeDTO sizeDTO){
        return new Size(
                sizeDTO.getSize_id(),
                sizeDTO.getName()
        );
    }
}
