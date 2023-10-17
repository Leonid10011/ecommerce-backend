package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.DiscountDTO;
import com.lbcoding.ecommerce.model.Discount;
import com.lbcoding.ecommerce.repository.DiscountRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.Set;

@ApplicationScoped
public class DiscountService {
    @Inject
    DiscountRepository discountRepository;
    @Inject
    UriInfo uriInfo;

    public Response create(DiscountDTO discountDTO){
        Set<String> errorMessages = DTOValidator.validateDTO(discountDTO);

        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        Discount existingDiscount = discountRepository.get(discountDTO.getCode());

        if(existingDiscount != null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Discount code already exists").build();
        }

        Discount discount = new Discount();
        discount.setCode(discountDTO.getCode());
        discount.setDescription(discountDTO.getDescription());
        discount.setDiscountType(discountDTO.getDiscountType());
        discount.setValue(discountDTO.getValue());
        discount.setStartDate(discountDTO.getStartDate());
        discount.setEndDate(discountDTO.getEndDate());

        discountRepository.create(discount);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(discount.getId()));

        return Response.created(builder.build()).entity(discount).build();
    }

    public Response get(Long id){
        Discount discount = discountRepository.get(id);

        if(discount != null){
            DiscountDTO discountDTO = discount.toDTO();

            return Response.status(Response.Status.OK).entity(discountDTO).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    public Response get(String code){
        Discount discount = discountRepository.get(code);

        if(discount != null){
            DiscountDTO discountDTO = discount.toDTO();

            return Response.status(Response.Status.OK).entity(discountDTO).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    public Response delete(Long id){
        Discount discount = discountRepository.get(id);
        if(discount != null){
            discountRepository.delete(id);

            return Response.noContent().build();
        }
         return  Response.status(Response.Status.BAD_REQUEST).build();
    }

}
