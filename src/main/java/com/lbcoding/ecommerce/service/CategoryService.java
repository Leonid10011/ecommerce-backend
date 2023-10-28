package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.CategoryDTO;
import com.lbcoding.ecommerce.exception.ResourceNotFoundException;
import com.lbcoding.ecommerce.model.Category;
import com.lbcoding.ecommerce.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import  com.lbcoding.ecommerce.service.validation.DTOValidator;

import jakarta.validation.*;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
@ApplicationScoped
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    UriInfo uriInfo;

    /**
     * @param id
     * @return Category Entity
     */
    public Response getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            CategoryDTO categoryDTO = category.get().toDTO();
            return Response.status(Response.Status.OK).entity(categoryDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * @param name
     * @return Category Entity
     */
    public Response getCategoryByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);

        if (category.isPresent()) {
            CategoryDTO categoryDTO = category.get().toDTO();
            return Response.status(Response.Status.OK).entity(categoryDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Create a category for products
     *
     * @param categoryDTO
     * @return Category
     */
    public Response createCategory(CategoryDTO categoryDTO) {

        Set<String> violations = DTOValidator.validateDTO(categoryDTO);

        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(violations)
                    .build();
        }


        Optional<Category> existingCategory = categoryRepository.findByName(categoryDTO.getName());

        if (existingCategory.isPresent()) {
            return Response.status(Response.Status.CONFLICT).entity("Category already exists").build();
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.create(category);

        // URI
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(category.getId()));

        return Response.created(builder.build()).entity(category).build();
    }

    /**
     * Retrieve a list of all categories
     *
     * @return List<CategoryDTO>
     */
    public Response getAllCategories() {
        List<Category> categories = categoryRepository.get();

        if (!categories.isEmpty()) {
            List<CategoryDTO> categoryDTOList = categories.stream()
                    .map(Category::toDTO)
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(categoryDTOList).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Delete category by id
     *
     * @param id
     * @return A success message
     */
    public Response deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()){
            categoryRepository.delete(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Category not found").build();
        }
    }
}
