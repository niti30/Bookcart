package com.bookstore.service;

import com.bookstore.model.Book;

import java.util.List;

/**
 * Service interface for Book operations
 */
public interface BookService {
    
    /**
     * Get all books in the bookstore
     * 
     * @return List of all books
     */
    List<Book> getAllBooks();
    
    /**
     * Get a book by its ID
     * 
     * @param id The ID of the book to find
     * @return The found book
     * @throws com.bookstore.exception.ResourceNotFoundException if no book with the given ID exists
     */
    Book getBookById(Long id);
    
    /**
     * Get a book by its ISBN
     * 
     * @param isbn The ISBN of the book to find
     * @return The found book
     * @throws com.bookstore.exception.ResourceNotFoundException if no book with the given ISBN exists
     */
    Book getBookByIsbn(String isbn);
    
    /**
     * Create a new book
     * 
     * @param book The book information to create
     * @return The created book with its generated ID
     */
    Book createBook(Book book);
    
    /**
     * Update an existing book
     * 
     * @param id The ID of the book to update
     * @param bookDetails The new book details
     * @return The updated book
     * @throws com.bookstore.exception.ResourceNotFoundException if no book with the given ID exists
     */
    Book updateBook(Long id, Book bookDetails);
    
    /**
     * Delete a book by its ID
     * 
     * @param id The ID of the book to delete
     * @return true if the book was deleted, false otherwise
     * @throws com.bookstore.exception.ResourceNotFoundException if no book with the given ID exists
     */
    boolean deleteBook(Long id);
    
    /**
     * Search for books by author
     * 
     * @param author The author to search for
     * @return List of books by the author
     */
    List<Book> findBooksByAuthor(String author);
    
    /**
     * Search for books by title
     * 
     * @param title The title to search for
     * @return List of books matching the title search
     */
    List<Book> findBooksByTitle(String title);
    
    /**
     * Search for books by genre
     * 
     * @param genre The genre to search for
     * @return List of books in the specified genre
     */
    List<Book> findBooksByGenre(Book.Genre genre);
}
