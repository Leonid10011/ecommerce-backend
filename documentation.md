# Database Entities Documentation
- [Category](#category-entity)
  - [controller](#category-controller)
- [Image](#image-entity)
  - [controller](#image-controller)
- [Size](#size-entity)
  - [controller](#size-entity)
# Category Entity Documentation

## Overview

### Purpose
This section focuses on the 'category' table, which is integral to our product management. It stores various product categories, such as t-shirts, hoodies, and more. This table acts as a centralized repository for categorizing our diverse product range.

### Relationships
In terms of database relationships, the 'category' table maintains a One-To-Many connection with the 'products' table. This design ensures that each product is associated with exactly one category, establishing a clear, unambiguous categorization system.

## Entity Implementation

### Field Description
- `category_id`: The unique identifier of the category.
- `name`: The name of the category.

### Constraint and Indexes
- None

## Data Transfer Object

### Category DTO

- `long` **category_id**: The category entity's id.
- `String` **name**: The category entity's name.


### Mapping Logic
Entity is mapped one to one on each field of the DTO.

## Repository Implementation

### Interface
```java
public interface ICategoryRepository {
    void create(Category category);
    Optional<Category> findById(Long id);
    List<Category> findAll();
    Optional<Category> findByName(String name);
    void delete(Long id);
    void update(Category category);
}
```

## Service

### Interface
```java
public interface ICategoryService {
    Response create(CategoryDTO categoryDTO);
    Response findAll();
    Response findById(Long id);
    Response findByName(String name);
    Response update(CategoryDTO categoryDTO);
    Response delete(Long id);
}
```

## Category Controller

### Endpoints

#### Get Category by Name

- **METHOD** `GET`
- **URL Path** `/api/category/{name}`
- **Request Parameters**
  - `name` (`String`): The name of the category to be retrieved
- **Success Response**
  - **Code** `200 OK`
  - **Content**
    ```json
    {
      "category_id": "id",
      "name": "{name}"
    }
    ```
- **Error Response**
  - **Code**: `404 NOT FOUND`
  - **Content**
    ```text
    Could not find category with name: "{name}"
    ```
  - **Code**: `400 BAD REQUEST`
  - **Content**
    ```text
    Should not happen
    ```
- **Example call**
  ```shell
  curl -X GET http://example.com/api/category/name -H "Content-Type: application/json"
  ```

#### Create Category

- **Method** `POST`
- **URL Path** `/api/category/`
- **Request Parameters**
  - None
- **Request Body**
  - [CategoryDTO](#category-dto) 
- **Success Response**
  - **Code** `201 CREATED`
  - **Content**
    ```text
    Category created
    ```
- **Error Response**
  - **Code** `409 CONFLICT`
    ```text
    Attempt to create a category that already exists: ${categoryDTO.getName()}
    ```
  - **Code** `500 INTERNAL SERVER ERROR`
    ```text
    Internal server error
    ```
- **Example call**
  ```shell
  curl -X POST http://example.com/api/category/ -H "Content-Type: application/json" -d '{"name": "New Category Name"}'
  ```

#### Update Category

- **Method** `PUT`
- **URL Path** `/api/category/`
- **Request Parameters**
  - None
- **Request Body**
  - [CategoryDTO](#category-dto)
- **Success Response**
  - **Code** `200 OK`
  - **Content**
    - `categoryDTO` with updated values
- **Error Response**
  - **Code** `404 NOT FOUND`
  - **Content**
    ```text
    Category not found with ID: ${category.getCategory_id()}
    ```

#### Delete Category

- **Method** `DELETE`
- **URL Path** `/api/category/{id}`
- **Request Parameters**
  - `id`: The unique identifier of the category to be deleted
- **Request Body**
  - None
- **Success Response**
  - **Code** `204 NO CONTENT`
  - **Content**
    ```text
    Category deleted successfully
    ```
- **Error Response**
  - **Code** `404 NOT FOUND`
  - **Content**
    ```text
    Category does not exist
    ```

# Image Entity

## Overview

### Purpose
This section focuses on the 'image' table, which is responsible for storing the images of our products. It stores all the images corresponding to a product.

### Relationship
In terms of database relationships, the 'product' table maintains a Many-To-One connection with the 'products' table. This design ensures that each product is associated with multiple images.

## Entity Implementation

### Field Description
- `image_id`: The unique identifier of the image.
- `product_id`: The unique identifier of the product this image belongs to.
- `url`: The location of the image for the corresponding product.

## Data Transfer Object

### Image DTO

- `long` **image_id**: The unique identifier of the image.
- `long` **product_id**: The unique identifier of the product this image belongs to.
- `String` **url**: The location of the image for the corresponding product.

## Repository Implementation

### Interface
```java
public interface IImageRepository {
  void create(Image image);
  Optional<Image> findById(long id);
  List<Image> findByProductId(long product_id);
  void delete(long id);
  void update(Image image);
}
```

## Service
### Interface
```java
public interface IImagesService {
  Response create(ImageDTO imageDTO);
  Response getByProduct(long product_id);
  Response update(ImageDTO imageDTO);
  Response delete(long id);
  Response getById(long id);
}
```

## Image Controller
### Get Image by ID

- **METHOD** `GET`
- **URL Path** `/api/images/{id}`
- **Request Paramters**
  - `id` `long` The id of the image to get
- **Request Body**
  - None
- **Success Response**
  - **Code** `200 OK`
  - **Content** 
    - [ImageDTO](#image-dto) `imageDTO` as json
- **Error Response**
  - **Code** `404 Not Found`
  - **Content**
    ```text
        Image not found for ID: `{id}`
    ```
### Get Image By Product ID
- **METHOD** `GET`
- **URL Path** `/api/images/{product_id}`
- **Request Parameters**
  - `id` `long` The id of the product the image belongs to
- **Request Body**
  - None
- **Success Response**
  - **Code** `200 OK`
  - **Content**
    - [ImageDTO](#image-dto) `imageDTO` as json
- **Error Response**
  - **Code** `404 Not Found`
  - **Content**
    ```text
        Image not found for ID: `{id}`
    ```
    
### Create Image

- **Method** `POST`
- **URL Path** `/api/image`
- **Request Body**
  - [ImageDTO](#image-dto) as JSON
- **Success Response**
  - **Code** `201 Created`
  - **Content**
    - [ImageDTO](#image-dto) as JSON that was created
- **Error Response**
  - **Code** `409 Conflict`
  - **Conent** 
    - ```text
        "Image for product_id " + imageDTO.getProduct_id() +  " and url " + imageDTO.getUrl() + " already exists"
      ```

### Update Image

- **Method** `PUT`
- **URL Path** `/api/image/`
- **Request Body**
  - [ImageDTO](#image-dto) 
- **Success Response**
  - **Code** `200 Ok`
  - **Content**
    - [ImageDTO](#image-dto) as JSON with updated values
- **Error Response**
  - **Code** `404 Not Found`
  - **Content**
  - ```text
      "Image does not exists with ID: " + image.getImage_id()
    ```
    
### Delete Image By Id

- **Method** `DELETE`
- **URL Path** `/api/image/{id}`
- **Request Parameter**
  - `long` **id** The id of the image to delete
- **Success Response**
  - **Code** `204 No Content`
  - **Content**
    - `None`
- **Error Response**
  - **Code** `404 Not Found`
  - **Content** 
    - ```text
        Image not found with ID: {id}
      ```

# Product Entity 

## Purpose 

The product entity represents the main data of an ecommerce application. It stores various basic information like name, price and a description. Additional information, like quantity and sizes is managed through joined tables like `product_size` or `prodcut_category`.

## Relationship
- Many-To-Many with `category` : Each product can belong to many categories
- One-To-Many with `image` : Each product belongs to many images. Each Image is unique to a product
- Many-To-Many with `size` : Each Product is available in many sizes.
- One-To-Many with `inventory` : Each Product has an inventory where it is stored.

# Size Entity

## Purpose 

The size represents a size characteristic of a `product`. 

## Relationship

We use it together with the product to define a specific `inventory`, which stores the quantity of a product specific size. 

## Data Transfer Object
### Size DTO
- `long` **size_id**: The unique identifier of the size.
- `String` **name**: The description of the size

## Repository Implementation
### Interface
```java
  public interface IImageRepository {
    void create(Image image);
    Optional<Image> findById(long id);
    List<Image> findByProductId(long product_id);
    void delete(long id);
    void update(Image image);
  }
```
## Service
### Interface
```java
  public interface IImagesService {
    Response create(ImageDTO imageDTO);
    Response getByProduct(long product_id);
    Response update(ImageDTO imageDTO);
    Response delete(long id);
    Response getById(long id);
  }
```

## Size Controller

### Get Size By ID
- **Method** `GET`
- **URL Path** `/api/size/{id}`
- **Request Parameter**
  - `long` **id** the unique identifier of the size
- **Success Response**
  - **Code** `200 Ok`
  - **Content**
    - [SizeDTO](#size-dto) as JSON
- **Error Response**
  - **Code** `404 Not Found`
  - **Content**
    - ```text
        Failed to find size with ID: {id}
      ```

### Get Size By NAME
- **Method** `GET`
- **URL Path** `/api/size/{name}`
- **Request Parameter**
  - `String` **name** the descriptor of the size
- **Success Response**
  - **Code** `200 Ok`
  - **Content**
    - [SizeDTO](#size-dto) as JSON
- **Error Response**
  - **Code** `404 Not Found`
  - **Content**
    - ```text
        Failed to find size with NAME: {name}
      ```

### Create Size
- **Method** `POST`
- **URL Path** `/api/size/`
- **Request Body**
  - [`SizeDTO`](#size-dto) the DTO holding the information
- **Success Response**
  - **Code** `201 Created`
  - **Content**
    - [`SizeDTO`](#size-dto) as JSON - The created size
- **Error Response**
  - **Code** `409 Conflict`
  - **Content**
    - ```text
        Size could not be created. Size with same name already exists."
      ```
- **Error Response**
  - **Code** `400 Bad Request`
  - **Content**
    - Validation error messages

### Update Size
- **Method** `PUT`
- **URL Path** `/api/size/`
- **Request Body**
  - [`SizeDTO`](#size-dto) the DTO holding the new information
- **Success Response**
  - **Code** `200 Ok`
  - **Content**
    - [`SizeDTO`](#size-dto) as JSON - sizeDTO with updated values
- **Error Response**
  - **Code** `404 Not Found`
  - **Content**
    - ```text
        Failed to find and update size with ID:  {sizeDTO.getSize_id()}
      ```
- **Error Response**
  - **Code** `400 Bad Request`
  - **Content**
    - Validation error messages

### Delete Size By Id

- **Method** `DELETE`
- **URL Path** `/api/sizes/{id}`
- **Request Parameter**
  - `long` **id** The id of the size to delete
- **Success Response**
  - **Code** `204 No Content`
  - **Content**
    - `None`
- **Error Response**
  - **Code** `404 Not Found`
  - **Content**
    - ```text
        Size not found with ID: {id}

# Inventory Entity

## Purpose

## Relationship

## Data Transfer Object
### Inventory DTO
- `long` **inventory_id**: The unique identifier of the inventory.
- `long` **product_id**: The ID of the product that is stored in this inventory
- `long` **size_id**: The ID of the size of the product that is strore in this inventory
- `int` **quantity**: The amount of the product for the size in this inventory
- `String` **location**: The name of the location where the inventory resides

## Repository Implementation
### Interface
```java
  public interface IInventoryRepository {
    void create(Inventory inventory);
    List<Inventory> findByProduct(long product_id);
    List<Inventory> findByProductAndSize(long product_id, long size_id);
    void update(Inventory inventory);
    void delete(long id);
    void setInventoryForProduct(Product product, int quantity);
  }
```
## Service
### Interface
```java
  public interface IInventoryService {
    Response create(InventoryDTO inventoryDTO);
    Response findById(long id);
    Response findByProductAndSize(InventoryDTO inventoryDTO);
    Response findByProduct(long product_id);
    Response update(InventoryDTO inventoryDTO);
    Response delete(long id);
  }
```

## Size Controller

### Get Inventory By ID
- **Method** `GET`
- **URL Path** `/api/inventory/{id}`
- **Request Parameter**
  - `long` **id** the unique identifier of the inventory
- **Success Response**
  - **Code** `200 Ok`
  - **Content**
    - [InventoryDTO](#inventory-dto) as JSON
- **Error Response**
  - **Code** `404 Not Found`
  - **Content**
    - ```text
        Failed to find inventory with ID: {id}
      ```

### Get Inventory By PRODUCT
- **Method** `GET`
- **URL Path** `/api/inventory/product/{id}`
- **Request Parameter**
  - `long` **id** the unique identifier of the product
- **Success Response**
  - **Code** `200 Ok`
  - **Content**
    - List<[InventoryDTO](#inventory-dto)> as JSON (Can be empty)

### Get Inventory By PRODUCT and SIZE
- **Method** `GET`
- **URL Path** `/api/inventory/findByProductAndSize`
- **Request Parameter**
  - `InventoryDTO` inventoryDTO containing the product and size ID
- **Success Response**
  - **Code** `200 Ok`
  - **Content**
    - List<[InventoryDTO](#inventory-dto)> as JSON (Can be empty)

### Post Inventory 
- **Method** `POST`
- **URL Path** `/api/inventory/`
- **Request Parameter**
  - `InventoryDTO` inventoryDTO containing the necessary data
- **Success Response**
  - **Code** `201 Ok`
  - **Content**
    - List<[InventoryDTO](#inventory-dto)> as JSON (Can be empty)
- **Error Response**
  - **Code** `400 Bad Request`
  - **Content**
    - ```text
        Error validating inventory
      ```
  - **Error Response**
  - **Code** `409 Conflict`
  - **Content**
    - ```text
        Inventory already exist with product, size and location combination
      ```

### Update Inventory
- **Method** `PUT`
- **URL Path** `/api/inventory/`
- **Request Parameter**
  - `InventoryDTO` inventoryDTO containing the necessary data
- **Success Response**
  - **Code** `201 Ok`
  - **Content**
    - List<[InventoryDTO](#inventory-dto)> as JSON (Can be empty)
- **Error Response**
  - **Code** `400 Bad Request`
  - **Content**
    - ```text
        Error validating inventory
      ```
  - **Error Response**
  - **Code** `404 Not Found`
  - **Content**
    - ```text
        Inventory not found with ID
      ```
### Delete Inventory
- **Method** `DELETE`
- **URL Path** `/api/inventory/{id}`
- **Request Parameter**
  - `InventoryDTO` inventoryDTO containing the necessary data
- **Success Response**
  - **Code** `204 No Content`
  - **Content**
    `None`