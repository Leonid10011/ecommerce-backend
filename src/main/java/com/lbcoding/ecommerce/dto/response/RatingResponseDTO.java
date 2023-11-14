package com.lbcoding.ecommerce.dto.response;

public class RatingResponseDTO {
    private Long id;
    private Long ratingId;  // For deletion purposes on frontend
    private Long userId;    // For sorting by user on frontend
    private Long productId; // For sorting by product on frontend
    private String productName;
    private String productDescription;
    // TODO
}
