package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.CategoryDTO;
import com.lbcoding.ecommerce.dto.request.CategoriesRequestDTO;
import com.lbcoding.ecommerce.service.CategoriesService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/category")
public class CategoryController {
    @Inject
    CategoriesService categoriesService;

    @GET
    @Path("/{name}")
    public Response getByName(@PathParam("name") String name){
        return categoriesService.findByName(name);
    }

    @POST
    @Path("/")
    public Response create(CategoriesRequestDTO categoryDTO){
        return categoriesService.create(categoryDTO);
    }

    @PUT
    @Path("/")
    public Response update(CategoryDTO categoryDTO){ return categoriesService.update(categoryDTO);}

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){ return categoriesService.delete(id);}

}
