# Product API

## Description
API for managing products in an e-commerce application.

## Endpoints

### Retrieve All Products
- **Endpoint:** `/product/get`
- **HTTP Method:** GET
- **Summary:** Retrieve all products.
- **Responses:**
  - `200`: Successful operation
  - `401`: Unauthorized

### Retrieve a Product by ID
- **Endpoint:** `/product/get/{id}`
- **HTTP Method:** GET
- **Summary:** Retrieve a product by ID.
- **Path Parameters:**
  - `id` (integer) - The product's identifier
- **Responses:**
  - `200`: Successful operation
  - `401`: Unauthorized
  - `404`: Product not found

### Search for Products by Name
- **Endpoint:** `/product/getByName/{name}`
- **HTTP Method:** GET
- **Summary:** Search for products by name.
- **Path Parameters:**
  - `name` (string) - The name of the product to search for
- **Responses:**
  - `200`: Successful operation
  - `401`: Unauthorized
  - `404`: No products found

### Create a New Product
- **Endpoint:** `/product/create`
- **HTTP Method:** POST
- **Summary:** Create a new product.
- **Request Body:** ProductDTO (application/json)
- **Responses:**
  - `201`: Product created
  - `400`: Bad request

### Delete a Product by ID
- **Endpoint:** `/product/delete/{id}`
- **HTTP Method:** DELETE
- **Summary:** Delete a product by ID.
- **Path Parameters:**
  - `id` (integer) - The product's identifier
- **Responses:**
  - `204`: Product deleted
  - `401`: Unauthorized
  - `404`: Product not found

## Data Structures

### ProductDTO
- **Type:** Object
- **Properties:**
  - `name` (string)
  - `description` (string)
  - `price` (number)
  - `category` (string)
- **Required Properties:**
  - `name`
  - `description`
  - `price`
  - `category`
