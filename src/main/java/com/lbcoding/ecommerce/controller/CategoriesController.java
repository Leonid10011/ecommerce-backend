package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.request.CategoriesRequestDTO;
import com.lbcoding.ecommerce.service.CategoriesService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/categories")
public class CategoriesController {
    @Inject
    CategoriesService categoriesService;

    @POST
    @Path("/")
    public Response create(CategoriesRequestDTO categoriesDTO){
        return categoriesService.create(categoriesDTO);
    }
}
