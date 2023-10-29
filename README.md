# Fullstack E-commerce Project - Backend

Welcome to the backend component of my Fullstack E-commerce portfolio project, designed for job applications. The corresponding frontend is available in a separate repository on my GitHub profile. This backend is responsible for providing a RESTful API that handles various aspects of the E-commerce platform.

## Overview

This backend component is built using Java and leverages the Quarkus framework along with JPA (Java Persistence API) to interact with a MySQL database. It offers the following functionalities through RESTful endpoints:

- **Authentication:** Implementing user authentication for secure access to the platform.

- **User Management:** Managing user accounts, user profiles, and user addresses.

- **Product Management:** Handling product details and categorization.

- **Order Management:** Managing customer orders, order history, and transactions.

- **Review System:** Allowing users to submit and view product reviews and ratings.

## Technologies Used

The project is built using the following technologies and tools:

- **Java:** As the primary programming language.

- **Quarkus:** A Kubernetes-native Java framework designed for the cloud, providing fast startup times and low memory consumption.

- **JPA (Java Persistence API):** For data persistence and management.

- **MySQL:** As the relational database for storing user data, product information, orders, and reviews.

- **Other libraries and dependencies:** Various Java libraries and packages to facilitate routing, validation, and more.
## TODO:  
### Cleaning Code for all Components and writing tests
**TODO clean code:**

- [x] Address
- [ ] CartItem
- [x] Category
- [ ] Discount
- [x] Inventory
- [ ] OrderHistory
- [x] OrderItem
- [x] Order
- [x] ProductImage
- [x] Product
- [ ] Rating
- [ ] Role
- [ ] Transaction
- [ ] UserCoupon
- [x] User

## Getting Started

To run this project locally or deploy it to a server, follow these steps:

1. Clone this repository to your local machine.

2. Configure your MySQL database and update the database connection settings in the project.

**application.properties that I use:  **

| Property                                | Value                                       |
|-----------------------------------------|---------------------------------------------|
| quarkus.http.port                       | `<port>`                                    |
| quarkus.datasource.jdbc.url             | `jdbc:mysql://localhost:3306/<databasename>` |
| quarkus.datasource.username              | `<username>`                                |
| quarkus.datasource.password              | `<password>`                                |
| quarkus.hibernate-orm.dialect           | `org.hibernate.dialect.MySQLDialect`      |
| quarkus.hibernate-orm.database.generation| `update`                                    |
| smallrye.jwt.enabled                    | `true`                                      |
| mp.jwt.verify.publickey.location         | `src/jwt/publicKey.pem`                     |
| mp.jwt.verify.issuer                    | `my-issuer`                                 |
| smallrye.jwt.sign.key.location           | `src/jwt/privateKey.pem`                    |
| smallrye.jwt.issuer                     | `my-app`                                   |
| smallrye.jwt.audience                   | `my-app`                                   |
| smallrye.jwt.duration                   | `3600`                                      |


3. Configure environment variables for sensitive information, such as authentication secrets.

4. Build and run the Quarkus application.

5. Access the API endpoints through the base URL and feel free to test the available functionality.

## API Endpoints

The API provides endpoints for interacting with different aspects of the E-commerce platform:

- `/users`: User profiles and addresses.
- `/product`: Product details and management.
- `/order`: Order creation, order history, and transactions.

For detailed information on each endpoint and its functionality, please refer to the project's API documentation.

## Frontend Repository

To explore the frontend component of this project, please visit the corresponding repository on my GitHub profile [here](link-to-frontend-repo).

## Contact

If you have any questions, feedback, or are interested in collaboration, please don't hesitate to reach out to me via email or through my GitHub profile.

Thank you for taking a look at my Fullstack E-commerce portfolio project!

