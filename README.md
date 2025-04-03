# Bookstore RESTful API

A Java-based RESTful API for a bookstore with CRUD operations, H2 database, and comprehensive testing.

## Features

- Complete CRUD operations for book resources
- Search functionality by title, author, and genre
- H2 in-memory database with 50 sample book entries
- Comprehensive test suite
- Spring Boot 3 with Java 17
- Swagger UI for API documentation
- Lombok for reducing boilerplate code

## Tech Stack

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5
- Swagger/OpenAPI 3

## Project Structure

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── bookstore
│   │   │           ├── controller
│   │   │           │   └── BookController.java
│   │   │           ├── exception
│   │   │           │   ├── GlobalExceptionHandler.java
│   │   │           │   └── ResourceNotFoundException.java
│   │   │           ├── model
│   │   │           │   └── Book.java
│   │   │           ├── repository
│   │   │           │   └── BookRepository.java
│   │   │           ├── service
│   │   │           │   ├── BookService.java
│   │   │           │   └── BookServiceImpl.java
│   │   │           └── BookstoreApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── data.sql
│   └── test
│       └── java
│           └── com
│               └── bookstore
│                   ├── controller
│                   │   └── BookControllerTest.java
│                   ├── repository
│                   │   └── BookRepositoryTest.java
│                   ├── service
│                   │   └── BookServiceTest.java
│                   └── BookstoreApplicationTests.java
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | /api/books | Get all books |
| GET    | /api/books/{id} | Get book by ID |
| GET    | /api/books/isbn/{isbn} | Get book by ISBN |
| POST   | /api/books | Create a new book |
| PUT    | /api/books/{id} | Update a book |
| DELETE | /api/books/{id} | Delete a book |
| GET    | /api/books/search/author?author={author} | Find books by author |
| GET    | /api/books/search/title?title={title} | Find books by title |
| GET    | /api/books/search/genre?genre={genre} | Find books by genre |

## Running Locally

```bash
# Clone the repository
git clone https://github.com/yourusername/bookstore-api.git
cd bookstore-api

# Run the application with Maven Wrapper
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8000`

- Swagger UI: http://localhost:8000/swagger-ui
- H2 Console: http://localhost:8000/h2-console
  - JDBC URL: jdbc:h2:mem:bookstoredb
  - Username: sa
  - Password: password

## Running Tests

```bash
./mvnw test
```

## Sample Book Data

The application includes 50 sample books across various genres in the `data.sql` file, which is automatically loaded when the application starts.