package com.bookstore.repository;

import com.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Book entity
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    /**
     * Find a book by its ISBN
     * 
     * @param isbn The ISBN to search for
     * @return Optional containing the book if found
     */
    Optional<Book> findByIsbn(String isbn);
    
    /**
     * Find all books by a specific author
     * 
     * @param author The author to search for
     * @return List of books by the author
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    /**
     * Find all books by title containing the search string (case insensitive)
     * 
     * @param title The title to search for
     * @return List of books matching the title search
     */
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Find all books by genre
     * 
     * @param genre The genre to search for
     * @return List of books in the specified genre
     */
    List<Book> findByGenre(Book.Genre genre);
}
