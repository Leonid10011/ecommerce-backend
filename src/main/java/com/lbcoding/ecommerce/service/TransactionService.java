package com.lbcoding.ecommerce.service;

import com.lbcoding.ecommerce.dto.TransactionDTO;
import com.lbcoding.ecommerce.model.Transaction;
import com.lbcoding.ecommerce.repository.TransactionRepository;
import com.lbcoding.ecommerce.service.validation.DTOValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.Set;

@ApplicationScoped
public class TransactionService {
    @Inject
    TransactionRepository transactionRepository;

    @Inject
    UriInfo uriInfo;

    public Response create(TransactionDTO transactionDTO){
        Set<String> errorMessages = DTOValidator.validateDTO(transactionDTO);

        if(!errorMessages.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        Transaction existingTransaction = transactionRepository.getByOrder(transactionDTO.getOrderId());

        if(existingTransaction != null){
            return Response.status(Response.Status.CONFLICT).entity("Transaction fo this order already exists").build();
        }

        Transaction transaction = new Transaction();
        transaction.setOrderId(transactionDTO.getOrderId());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setPaymentMethod(transactionDTO.getPaymentMethod());
        transaction.setAmount(transactionDTO.getAmount());

        transactionRepository.create(transaction);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(transaction.getId()));

        return Response.created(builder.build()).entity(transaction).build();
    }

    public Response get(Long id){
        Transaction transaction = transactionRepository.getByOrder(id);

        if(transaction != null){
            TransactionDTO transactionDTO = transaction.toDTO();

            return Response.status(Response.Status.OK).entity(transactionDTO).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Response getByOrder(Long orderId){
        Transaction transaction = transactionRepository.getByOrder(orderId);

        if(transaction != null){
            TransactionDTO transactionDTO = transaction.toDTO();

            return Response.status(Response.Status.OK).entity(transactionDTO).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Response delete(Long id){
        Transaction transaction = transactionRepository.get(id);

        if(transaction != null){
            transactionRepository.delete(id);

            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Transaction not found").build();
    }

}
