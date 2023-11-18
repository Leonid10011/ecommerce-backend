package com.lbcoding.ecommerce.dto.response;

public class RatingResponseDTO {
    private Long id;
    private Long ratingId;  // For deletion purposes on frontend
    private Long userId;    // For sorting by user on frontend
    private Long productId; // For sorting by product on frontend
    private String name;
    private String description;
    private String categoryName;
    private String sizeDescription;
    private String imageUrl;
    private String price;

}
