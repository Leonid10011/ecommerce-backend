# Favorite Item API

# Favorite Item API

## Description
API for managing favorite items in an e-commerce application.

## Endpoints

### Create Favorite Item
- **Endpoint:** `/favoriteItem`
- **HTTP Method:** POST
- **Summary:** Create a favorite item.
- **Request Body:** FavoriteProductDTO (application/json)
- **Responses:**
  - `201`: Favorite item created
  - `400`: Bad request

### Get Favorite Items by User ID
- **Endpoint:** `/favoriteItem/get/{userId}`
- **HTTP Method:** GET
- **Summary:** Get favorite items by user ID.
- **Path Parameters:**
  - `userId` (integer) - The user's identifier
- **Responses:**
  - `200`: Successful operation
  - `401`: Unauthorized
  - `404`: Favorite items not found

### Delete Favorite Item by ID
- **Endpoint:** `/favoriteItem/delete/{id}`
- **HTTP Method:** DELETE
- **Summary:** Delete a favorite item by ID.
- **Path Parameters:**
  - `id` (integer) - The favorite item's identifier
- **Responses:**
  - `204`: Favorite item deleted
  - `401`: Unauthorized
  - `404`: Favorite item not found

## Data Structures

### FavoriteProductDTO
- **Type:** Object
- **Properties:**
  - `userId` (Long)
  - `productId` (Long)
- **Required Properties:**
  - `userId`
  - `productId`

