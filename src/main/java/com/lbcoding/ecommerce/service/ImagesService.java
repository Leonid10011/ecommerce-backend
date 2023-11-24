package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.ImageDTO;
import com.lbcoding.ecommerce.model.Image;
import com.lbcoding.ecommerce.repository.ImagesRepository;
import com.lbcoding.ecommerce.service.inerfaces.IImagesService;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NonUniqueResultException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            return Response.status(Response.Status.CREATED).entity(newImage).build();
        } catch ( NonUniqueResultException e) {
            String errorMessage = "Image for product_id " + imageDTO.getProduct_id() +  " and url " + imageDTO.getUrl() + " already exists";
            logger.warn(errorMessage);
            return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();
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
