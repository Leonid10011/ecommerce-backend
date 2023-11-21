# Database Entities Documentation
## Category Entity

### Overview 
`Purpose`: This section focuses on the 'category' table, which is integral to our product management. It stores various product categories, such as t-shirts, hoodies, and more. This table acts as a centralized repository for categorizing our diverse product range.
`Relationships`: In terms of database relationships, the 'category' table maintains a One-To-Many connection with the 'products' table. This design ensures that each product is associated with exactly one category, establishing a clear, unambiguous categorization system.

### Entity Implementation
#### Entity Diagram

![Local Image](dist/category_er.png "Category ER")

#### Field Description
`category_id`: The unique identifier of the category

`name`: The name of the category

#### Constraint and indexes
`none`

### Data Transfer Object
#### DTO Structure

`long` `category_id`: The category entities id.

`String` `name`: The category entities name.

#### Mapping Logic

Entity is mapped one to one on each field of the DTO.

### Repository Implementation
#### Interface
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

### Service
