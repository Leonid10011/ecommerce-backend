package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.CategoryDTO;
import com.lbcoding.ecommerce.model.Category;
import com.lbcoding.ecommerce.repository.interfaces.ICategoryRepository;
import com.lbcoding.ecommerce.service.inerfaces.ICategoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NonUniqueResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategoryService implements ICategoryService {
    public final static Logger logger = LoggerFactory.getLogger(CategoryService.class);
    @Inject
    ICategoryRepository categoryRepository;

    /**
     * Attempts to create a new category entry with the given DTO.
     * @param categoryDTO The DTO that as the information that will be used to create a new Category entity.
     * @return status code 201 on success. Status code 409 if such category already exists. 500 if something unexpected happens.
     */
    @Transactional
    public Response create(CategoryDTO categoryDTO){
        logger.info("Received request create Categories");
        try {
            Category category = new Category();
            category.setName(categoryDTO.getName());
            categoryRepository.create(category);
            return Response.status(Response.Status.CREATED).entity("Category created").build();
        } catch ( NonUniqueResultException e) {
            logger.warn("Attempt to create a category that already exists: " + categoryDTO.getName());
            return Response.status(Response.Status.CONFLICT).entity("Attempt to create a category that already exists: " + categoryDTO.getName()).build();
        } catch ( Exception e) {
            logger.error("Unexpected error occurred while creating category: " + categoryDTO.getName());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    /**
     * Attempts to find alle categories.Will always return a list of categories. If none were found returns an empty list.
     * @return Returns a Response Object with either a list with found categories or an empty list with status code 200.
     */
    public Response findAll(){
        logger.info("Received request to find all categories");
        List<Category> categoryList = categoryRepository.findAll();
        logger.info("Successfully found categories");
        return Response.status(Response.Status.OK).entity(categoryList).build();
    }

    /**
     * Attempts to find a category with given ID.
     * @param id The unique identifier of the category.
     * @return A category with the given id and status code 200.
     *          Otherwise, returns status code 404.
     */
    public Response findById(Long id){
        logger.info("Received request to find category by ID: " + id);
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) {
            logger.info("Category not found with ID: " + id);
            return Response.status(Response.Status.NOT_FOUND).entity("Could not find category with name: " + id).build();
        } else {

            logger.info("Successfully found category with ID: " + id);
            return Response.status(Response.Status.OK).entity(category.get()).build();
        }
    }

    /**
     * Attempts to retrieve a category with given name parameter.
     * @param name The name of the category to find
     * @return The category with the given name and a status code 200.
     *          If category does not exist, return status code 404.
     *          If (shouldn't happen) multiple entries with that name exist throw an NonUniqueResultException with status code 400
     */
    public Response findByName(String name){
        logger.info("Received request to find category by name: " + name);
        try {
            Optional<Category> category = categoryRepository.findByName(name);
            if(category.isEmpty()){
                logger.warn("Category not found with name: " + name);
                return Response.status(Response.Status.NOT_FOUND).entity("Could not find category with name: " + name).build();
            } else {
                logger.info("Successfully found category with name: " + name);
                CategoryDTO categoryDTO = categoryEntityToDTO(category.get());
                return Response.status(Response.Status.OK).entity(categoryDTO).build();
            }
        } catch( NonUniqueResultException e) {
            logger.error("Exceptional Error: " + e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Should not happen").build();
        }
    }

    /**
     * Updates the values of category. I.e. updates the name attribute passed in the categoryDTO
     * @param categoryDTO The DTO that has the information that will be updated
     * @return The updated categoryDTO with status code 200.
     *  Or returns NOT_FOUND (404) status code when category does not exist.
     */
    public Response update(CategoryDTO categoryDTO){
        logger.info("Received request to find update category by ID: " + categoryDTO.getCategory_id());
        Category category = categoryDTOtoEntity(categoryDTO);
        try {
            categoryRepository.update(category);
            logger.info("Successfully updated category with ID: " + category.getCategory_id());
            CategoryDTO responseDTO = categoryEntityToDTO(category);
            return Response.status(Response.Status.OK).entity(responseDTO).build();
        } catch( NotFoundException e) {
            logger.warn("Category not found with ID: " + categoryDTO.getCategory_id());
            return Response.status(Response.Status.NOT_FOUND).entity("Category not found with ID: " + category.getCategory_id()).build();
        }
    }

    /**
     * Attempts to delete a category by its id.
     * @param id category_id
     * @return noContent on success. NOT_FOUND (404) status code if not category does not exist.
     */
    public Response delete(Long id){
        logger.info("Received request to delete category by ID: " + id);
        try {
            categoryRepository.delete(id);
            logger.info("successfully deleted category with ID: " + id);
            return Response.noContent().entity("Category deleted successfully").build();
        } catch( NotFoundException e ) {
            logger.warn("Category not found with ID: " + id);
            return Response.status(Response.Status.NOT_FOUND).entity("Category does not exists").build();
        }
    }

    /**
     * Helper Function
     * @param category The category that needs to be transformed to a DTO
     * @return A DTO with the category's information.
     */
    private CategoryDTO categoryEntityToDTO(Category category){
        return new CategoryDTO(
                category.getCategory_id(),
                category.getName()
        );
    }

    /**
     * Helper Function
     * @param categoryDTO The DTO that has the information for the creation of the entity
     * @return A category entity with the information of the provided DTO.
     */
    private Category categoryDTOtoEntity(CategoryDTO categoryDTO){
        return new Category(
                categoryDTO.getCategory_id(),
                categoryDTO.getName()
        );
    }
}
