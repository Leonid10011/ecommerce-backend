package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.CategoryDTO;
import com.lbcoding.ecommerce.service.CategoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/category")
public class CategoryController {

    @Inject
    CategoryService categoryService;

    @GET
    @Path("/get/{id}")
    public Response get(Long id){
        return categoryService.getCategory(id);
    }

    @GET
    @Path("/get/{name}")
    public Response get(String name){
        return categoryService.getCategory(name);
    }

    @GET
    @Path("/get")
    public Response get(){
        return categoryService.getAllCategories();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @DELETE
    @Path("/delete")
    public Response delete(Long id){
        return categoryService.deleteCategory(id);
    }
}
