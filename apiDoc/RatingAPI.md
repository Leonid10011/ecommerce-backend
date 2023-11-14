# RatingController Documentation

## Overview
`RatingController` is a RESTful controller in the `com.lbcoding.ecommerce.controller` package responsible for handling rating-related HTTP requests. It interacts with `RatingService` to provide various operations on `Rating` entities.

## Endpoints

### 1. Get Ratings by User
- **Path**: `/rating/get_user/{id}`
- **Method**: `GET`
- **Description**: Retrieves a list of ratings made by a specific user.
- **Path Parameters**:
    - `id` (Long): The ID of the user.
- **Response**:
    - Status 200 (OK) with a list of `RatingDTO` if ratings are found.
    - Status 400 (Bad Request) if no ratings are found.

### 2. Get Ratings by Product
- **Path**: `/rating/get_product/{id}`
- **Method**: `GET`
- **Description**: Retrieves a list of ratings for a specific product.
- **Path Parameters**:
    - `id` (Long): The ID of the product.
- **Response**:
    - Status 200 (OK) with a list of `RatingDTO` if ratings are found.
    - Status 400 (Bad Request) if no ratings are found.

### 3. Create a Rating
- **Path**: `/rating/`
- **Method**: `POST`
- **Description**: Creates a new rating.
- **Request Body**:
    - `RatingDTO`: The DTO object representing the new rating.
- **Response**:
    - Status 201 (Created) with the created `Rating` entity.
    - Status 400 (Bad Request) if the DTO validation fails.
    - Status 409 (Conflict) if a rating from the user for the product already exists.

### 4. Update Rating Value
- **Path**: `/rating/updateRatingValue`
- **Method**: `PUT`
- **Description**: Updates the value of an existing rating.
- **Request Body**:
    - `RatingDTO`: The DTO object with the updated rating value.
- **Response**:
    - Status 200 (OK) with a success message.
    - Status 404 (Not Found) if the rating is not found.

### 5. Update Rating Text
- **Path**: `/rating/updateRatingText`
- **Method**: `PUT`
- **Description**: Updates the text of an existing rating.
- **Request Body**:
    - `RatingDTO`: The DTO object with the updated rating text.
- **Response**:
    - Status 200 (OK) with a success message.
    - Status 404 (Not Found) if the rating is not found.

### 6. Delete a Rating
- **Path**: `/rating/delete`
- **Method**: `DELETE`
- **Description**: Deletes an existing rating.
- **Query Parameters**:
    - `id` (Long): The ID of the rating to delete.
- **Response**:
    - Status 204 (No Content) if the deletion is successful.
    - Status 400 (Bad Request) if the rating is not found.

## Dependencies
- `RatingService`: Service class providing business logic for rating operations.
- `RatingDTO`: Data Transfer Object for transferring rating data.
