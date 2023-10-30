# Address API

## Description
API for managing user addresses in an e-commerce application.

## Endpoints

### Get Address by ID
- **Endpoint:** `/address/get/{id}`
- **HTTP Method:** GET
- **Summary:** Get address by ID.
- **Path Parameters:**
    - `id` (integer) - The address's identifier
- **Responses:**
    - `200`: Successful operation
    - `401`: Unauthorized
    - `404`: Address not found

### Create a New Address
- **Endpoint:** `/address/create`
- **HTTP Method:** POST
- **Summary:** Create a new address.
- **Request Body:** AddressDTO (application/json)
- **Responses:**
    - `201`: Address created
    - `400`: Bad request

### Delete Address by ID
- **Endpoint:** `/address/delete/{id}`
- **HTTP Method:** DELETE
- **Summary:** Delete an address by ID.
- **Path Parameters:**
    - `id` (integer) - The address's identifier
- **Responses:**
    - `204`: Address deleted
    - `401`: Unauthorized
    - `404`: Address not found

## Data Structures

### AddressDTO
- **Type:** Object
- **Properties:**
    - `street` (string)
    - `city` (string)
    - `postalCode` (string)
    - `country` (string)
- **Required Properties:**
    - `street`
    - `city`
    - `postalCode`
    - `country`
