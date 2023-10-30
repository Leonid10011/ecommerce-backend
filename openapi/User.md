# E-Commerce API

## Description
API for managing users and user profiles in an e-commerce application.

## Version
- Version: 1.0.0

## Endpoints

### Get Current User
- **Endpoint:** `/user/get`
- **HTTP Method:** GET
- **Summary:** Get current user
- **Responses:**
    - `200`: Successful operation
    - `401`: Unauthorized

### Get User by Username
- **Endpoint:** `/user/get/{username}`
- **HTTP Method:** GET
- **Summary:** Get user by username
- **Path Parameters:**
    - `username` (string) - The username to retrieve
- **Responses:**
    - `200`: Successful operation
    - `401`: Unauthorized
    - `404`: User not found

### Create a New User
- **Endpoint:** `/user`
- **HTTP Method:** POST
- **Summary:** Create a new user
- **Request Body:** UserDTO (application/json)
- **Responses:**
    - `201`: User created
    - `400`: Bad request

### Create a User Profile
- **Endpoint:** `/user/createProfile`
- **HTTP Method:** POST
- **Summary:** Create a user profile
- **Request Body:** UserProfileDTO (application/json)
- **Responses:**
    - `201`: User profile created
    - `400`: Bad request

### Delete User by ID
- **Endpoint:** `/user/delete/{id}`
- **HTTP Method:** DELETE
- **Summary:** Delete user by ID
- **Path Parameters:**
    - `id` (integer) - The user's identifier
- **Responses:**
    - `204`: User deleted
    - `401`: Unauthorized
    - `404`: User not found

### Delete User Profile by ID
- **Endpoint:** `/user/deleteProfile/{id}`
- **HTTP Method: DELETE
- **Summary:** Delete user profile by ID
- **Path Parameters:**
    - `id` (integer) - The user's identifier
- **Responses:**
    - `204`: User profile deleted
    - `401`: Unauthorized
    - `404`: User profile not found

### User Login
- **Endpoint:** `/user/login`
- **HTTP Method:** POST
- **Summary:** User login
- **Request Body:** CredentialDTO (application/json)
- **Responses:**
    - `200`: Successful login (text/plain)
    - `401`: Unauthorized

## Data Structures

### UserDTO
- **Type:** Object
- **Properties:**
    - `username` (string)
    - `email` (string)
    - `password` (string)
- **Required Properties:**
    - `username`
    - `email`
    - `password`

### UserProfileDTO
- **Type:** Object
- **Properties:**
    - `name` (string)
    - `address` (string)
- **Required Properties:**
    - `name`
    - `address`

### CredentialDTO
- **Type:** Object
- **Properties:**
    - `username` (string)
    - `password` (string)
- **Required Properties:**
    - `username`
    - `password`
