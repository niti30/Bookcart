package com.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Main application class for Bookstore API
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Bookstore API",
        version = "1.0",
        description = "REST API for a bookstore with CRUD operations"
    )
)
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }
}
