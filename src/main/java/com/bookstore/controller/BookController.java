package com.bookstore.controller;

import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Book operations
 */
@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Controller", description = "CRUD operations for books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the bookstore")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID", description = "Retrieve a specific book by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the book"),
        @ApiResponse(responseCode = "404", description = "Book not found with the given ID", content = @Content)
    })
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "ID of the book to retrieve") @PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Get a book by ISBN", description = "Retrieve a specific book by its ISBN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the book"),
        @ApiResponse(responseCode = "404", description = "Book not found with the given ISBN", content = @Content)
    })
    public ResponseEntity<Book> getBookByIsbn(
            @Parameter(description = "ISBN of the book to retrieve") @PathVariable String isbn) {
        Book book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    @Operation(summary = "Create a new book", description = "Add a new book to the bookstore")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Book successfully created",
                content = @Content(schema = @Schema(implementation = Book.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<Book> createBook(
            @Parameter(description = "Book details to create") @Valid @RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Update an existing book's information or create it with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book successfully updated"),
        @ApiResponse(responseCode = "201", description = "Book successfully created with specified ID"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "ID of the book to update or create") @PathVariable Long id,
            @Parameter(description = "Book details") @Valid @RequestBody Book bookDetails) {
        boolean bookExists = true;
        try {
            bookService.getBookById(id);
        } catch (ResourceNotFoundException e) {
            bookExists = false;
        }
        
        Book updatedBook = bookService.updateBook(id, bookDetails);
        
        if (bookExists) {
            return ResponseEntity.ok(updatedBook);
        } else {
            return new ResponseEntity<>(updatedBook, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Delete a book from the bookstore")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Book not found with the given ID", content = @Content)
    })
    public ResponseEntity<Map<String, Boolean>> deleteBook(
            @Parameter(description = "ID of the book to delete") @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(Map.of("deleted", true));
    }

    @GetMapping("/search/author")
    @Operation(summary = "Search books by author", description = "Find books by a specific author")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved books by the author")
    public ResponseEntity<List<Book>> getBooksByAuthor(
            @Parameter(description = "Author name to search for") @RequestParam String author) {
        List<Book> books = bookService.findBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/title")
    @Operation(summary = "Search books by title", description = "Find books by title")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved books by title")
    public ResponseEntity<List<Book>> getBooksByTitle(
            @Parameter(description = "Title to search for") @RequestParam String title) {
        List<Book> books = bookService.findBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/genre")
    @Operation(summary = "Search books by genre", description = "Find books by genre")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved books by genre")
    public ResponseEntity<List<Book>> getBooksByGenre(
            @Parameter(description = "Genre to search for") @RequestParam Book.Genre genre) {
        List<Book> books = bookService.findBooksByGenre(genre);
        return ResponseEntity.ok(books);
    }
}
