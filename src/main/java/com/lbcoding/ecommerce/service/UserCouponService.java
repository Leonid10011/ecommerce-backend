package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.UserCouponDTO;
import com.lbcoding.ecommerce.model.UserCoupon;
import com.lbcoding.ecommerce.repository.UserCouponRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserCouponService {
    @Inject
    UserCouponRepository userCouponRepository;

    @Inject
    UriInfo uriInfo;

    public Response create(UserCouponDTO userCouponDTO){
        Set<String> errorMessages = DTOValidator.validateDTO(userCouponDTO);

        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        UserCoupon existingUserCoupon = userCouponRepository.get(userCouponDTO.getUserId(), userCouponDTO.getDiscountId());

        if(existingUserCoupon != null){
            return Response.status(Response.Status.CONFLICT).build();
        }

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userCouponDTO.getUserId());
        userCoupon.setDiscountId(userCouponDTO.getDiscountId());

        userCouponRepository.create(userCoupon);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(userCoupon.getId()));

        return Response.created(builder.build()).entity(userCoupon).build();
    }

    public Response getByUser(Long id){
        List<UserCoupon> userCouponList = userCouponRepository.getByUser(id);

        if(!userCouponList.isEmpty()){
            List<UserCouponDTO> userCouponDTOList = userCouponList.stream()
                    .map(userCoupon -> userCoupon.toDTO())
                    .collect(Collectors.toList());
            return Response.status(Response.Status.OK).entity(userCouponDTOList).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    public Response getByDiscount(Long id){
        List<UserCoupon> userCouponList = userCouponRepository.getByDiscount(id);

        if(!userCouponList.isEmpty()){
            List<UserCouponDTO> userCouponDTOList = userCouponList.stream()
                    .map(userCoupon -> userCoupon.toDTO())
                    .collect(Collectors.toList());
            return Response.status(Response.Status.OK).entity(userCouponDTOList).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    public Response get(Long id){
        UserCoupon userCoupon = userCouponRepository.get(id);

        if(userCoupon != null){
            return Response.status(Response.Status.OK).entity(userCoupon).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    public Response delete(Long id){
        UserCoupon userCoupon = userCouponRepository.get(id);

        if(userCoupon != null) {
            userCouponRepository.delete(id);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
