# Bookstore RESTful API

A Java-based RESTful API for a bookstore with CRUD operations, H2 database, and comprehensive testing.

## Features

- Complete RESTful API for book management
- CRUD operations (Create, Read, Update, Delete)
- Ability to fetch a single book or a list of books
- Search functionality by author, title, and genre
- In-memory H2 database
- 50 sample book records
- Comprehensive test coverage
- API documentation with Swagger UI

## Technology Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5
- Swagger / OpenAPI 3.0

## Requirements

- Java 17 or higher
- Maven

## Running the Application

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/bookstore-api.git
   cd bookstore-api
   ```

2. Build the application:
   ```
   mvn clean package
   ```

3. Run the application:
   ```
   java -jar target/bookstore-api-1.0.0.jar
   ```

   The API will start on port 8000 and can be accessed at `http://localhost:8000`.

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:

