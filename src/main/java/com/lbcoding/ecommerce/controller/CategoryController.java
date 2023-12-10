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
    CategoryService categoriesService;

    @GET
    @Path("/getByName")
    public Response getByName(@QueryParam("name") String name){
        return categoriesService.findByName(name);
    }

    @GET
    @Path("/")
    public  Response getAll(){
        return categoriesService.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/")
    public Response create(CategoryDTO categoryDTO){
        return categoriesService.create(categoryDTO);
    }

    @PUT
    @Path("/")
    public Response update(CategoryDTO categoryDTO){ return categoriesService.update(categoryDTO);}

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){ return categoriesService.delete(id);}

}
