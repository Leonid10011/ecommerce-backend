package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.ImageDTO;
import com.lbcoding.ecommerce.model.Image;
import com.lbcoding.ecommerce.repository.ImagesRepository;
import com.lbcoding.ecommerce.service.inerfaces.IImagesService;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class ImagesService implements IImagesService {
    @Inject
    ImagesRepository imagesRepository;

    private static final Logger logger = LoggerFactory.getLogger(ImagesService.class);

    /**
     * Handles the creation of a new image entity from the provided ImageDTO. This method performs the following steps:
     * 1. Validates the ImageDTO object using DTOValidator. If validation fails, returns a BAD_REQUEST response with the validation error messages.
     * 2. Converts the ImageDTO to an Image entity.
     * 3. Tries to persist the Image entity using the imagesRepository. If an image with the same product_id and url already exists, catches a NonUniqueResultException and returns a CONFLICT response with a relevant error message.
     * 4. If the image is successfully persisted, returns a CREATED response with the persisted Image entity.
     *
     * @param imageDTO The ImageDTO object containing the data for the image to be created.
     * @return A Response object representing the outcome of the operation. This can be a success response with the created image data, a conflict error, or a bad request error.
     */
    @Override
    public Response create(ImageDTO imageDTO) {
        Set<String> errorMessages = DTOValidator.validateDTO(imageDTO);
        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        try {
            logger.info("Received request to persist image");
            Image newImage = imageDTOtoEntity(imageDTO);
            imagesRepository.create(newImage);
            logger.info("Successfully persisted image");
            ImageDTO resImage = imageEntityToDTO(newImage);
            return Response.status(Response.Status.CREATED).entity(resImage).build();
        } catch ( NonUniqueResultException e) {
            String errorMessage = "Image for product_id " + imageDTO.getProduct_id() +  " and url " + imageDTO.getUrl() + " already exists";
            logger.warn(errorMessage);
            return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();
        }
    }

    /**
     * Handles the retrieval of images for a product. If images are found returns a list of images and status code 200.
     * When the list is empty sends a message and a status code 404, to allow the client handle no images, like a "no image found" image.
     * @param product_id The unique identifier of the product.
     * @return List<Image> and status code 200 on Success.
     *  Empty List and status 404 when nothing was found.
     */
    @Override
    public Response getByProduct(long product_id) {
        logger.info("Received request to retrieve images for product ID: " + product_id);
        List<Image> images = imagesRepository.findByProductId(product_id);
        if(images.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity("No images found for product ID: " + product_id).build();
        } else {
            return Response.status(Response.Status.OK).entity(images).build();
        }
    }

    /**
     * Attempts to update the values of an existing image in the database.
     * @param imageDTO The DTO containing the new values for the image
     * @return The update Image as DTO and status code 200 on success.
     * On failure returns a message and status code 404.
     */
    @Override
    public Response update(ImageDTO imageDTO){
        Set<String> errorMessage = DTOValidator.validateDTO(imageDTO);
        if(!errorMessage.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        Image image = imageDTOtoEntity(imageDTO);

        try {
            imagesRepository.update(image);
            ImageDTO responseImage = imageEntityToDTO(image);

            return Response.status(Response.Status.OK).entity(responseImage).build();
        } catch( NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Image does not exists with ID: " + image.getImage_id()).build();
        }
    }

    @Override
    public Response delete(long id){
        try {
            imagesRepository.delete(id);
            return Response.noContent().entity("Image deleted successfully").build();
        } catch( EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Image not found with ID: " + id).build();
        }
    }

    /**
     * Attempts to find an image for the given ID.
     * @param id The unique identifier of the image to find
     * @return On success returns a Response containing the found image as DTO and status code 200.
     * On Failure returns a message and status code 404.
     */
    @Override
    public Response getById(long id) {
        Optional<Image> image = imagesRepository.findById(id);
        if(image.isPresent()){
            ImageDTO imageDTO = imageEntityToDTO(image.get());
            return Response.status(Response.Status.OK).entity(imageDTO).build();
        } else {
            // When image is empty, it means none was found for that ID
            return Response.status(Response.Status.NOT_FOUND).entity("Image not found for ID: " + id).build();
        }
    }

    private ImageDTO imageEntityToDTO(Image image){
        return new ImageDTO(
                image.getImage_id(),
                image.getProduct_id(),
                image.getImageUrl()
        );
    }

    private Image imageDTOtoEntity(ImageDTO imageDTO){
        return new Image(
                imageDTO.getImage_id(),
                imageDTO.getProduct_id(),
                imageDTO.getUrl()
        );
    }
}
