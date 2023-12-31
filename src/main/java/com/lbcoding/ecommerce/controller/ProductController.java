package com.lbcoding.ecommerce.controller;

import com.lbcoding.ecommerce.dto.OrderItemDTO;
import com.lbcoding.ecommerce.dto.RatingDTO;
import com.lbcoding.ecommerce.dto.request.ProductsRequestDTO;
import com.lbcoding.ecommerce.service.OrderItemService;
import com.lbcoding.ecommerce.service.ProductsService;
import com.lbcoding.ecommerce.service.RatingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/products")
public class ProductController {
    @Inject
    ProductsService productsService;
    @Inject
    RatingService ratingService;
    @Inject
    OrderItemService orderItemService;
    @POST
    @Path("/")
    public Response create(ProductsRequestDTO productsRequestDTO){
        return productsService.create(productsRequestDTO);
    }

    @POST
    @Path("/ratings")
    public Response createRating(RatingDTO ratingDTO){
        return ratingService.create(ratingDTO);
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id){
        return productsService.getById(id);
    }

    @GET
    @Path("/")
    public Response getAll(
        @QueryParam("page") int page,
        @QueryParam("pageSize") int pageSize){

        return productsService.getAll(page, pageSize);
    }

    @GET
    @Path("/getByName")
    public Response getByName(@QueryParam("name") String name){
        return productsService.getByName(name);
    }

    @GET
    @Path("/ratings/{id}")
    public Response getRatingValueForProduct(@PathParam("id") long id){
        return ratingService.getRatingValue(id);
    }
    @GET
    @Path("/ratings/product/{id}")
    public Response getRatingsForProduct(@PathParam("id") long id){
        return ratingService.getRatingsForProduct(id);
    }
    @GET
    @Path("/ratings/user/{id}")
    public Response getRatingsForUser(@PathParam("id") long id){
        return ratingService.getRatingsForUser(id);
    }
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") long id){
        return productsService.delete(id);
    }

    @POST
    @Path("/orderItem")
    public Response createOrderItem(OrderItemDTO orderItemDTO){
        return orderItemService.create(orderItemDTO);
    }
    @PUT
    @Path("/orderItem")
    public Response updateOrderItem(OrderItemDTO orderItemDTO){
        return orderItemService.update(orderItemDTO);
    }
    @GET
    @Path("/orderItem/findByOrderId/{id}")
    public Response findByOrder(@PathParam("id") long orderId){
        return orderItemService.findByOrder(orderId);
    }
    @DELETE
    @Path("/orderItem/{orderId}/{productId}")
    public Response delete(@PathParam("orderId") long orderId, @PathParam("productId") long productId){
        return orderItemService.delete(orderId, productId);
    }
}
