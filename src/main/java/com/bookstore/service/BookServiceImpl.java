package com.bookstore.service;

import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of BookService interface
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
    }

    @Override
    @Transactional
    public Book createBook(Book book) {
        // Check if ISBN already exists
        bookRepository.findByIsbn(book.getIsbn()).ifPresent(existingBook -> {
            throw new IllegalArgumentException("Book with ISBN " + book.getIsbn() + " already exists");
        });
        
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, Book bookDetails) {
        Book book;
        boolean isNewBook = false;
        
        try {
            // Try to find the book first
            book = getBookById(id);
        } catch (ResourceNotFoundException e) {
            // If book doesn't exist, create a new one with the specified ID
            book = new Book();
            book.setId(id); // Explicitly set the ID to the requested ID
            isNewBook = true;
            
            // Check if a book with this ID already exists (safety check)
            if (bookRepository.existsById(id)) {
                throw new IllegalArgumentException("Book with ID " + id + " already exists");
            }
        }
        
        // Always check ISBN uniqueness when ISBN is provided, but handle differently for new vs existing books
        if (bookDetails.getIsbn() != null) {
            // If this is a new book or we're changing the ISBN of an existing book
            if (isNewBook || book.getIsbn() == null || !bookDetails.getIsbn().equals(book.getIsbn())) {
                bookRepository.findByIsbn(bookDetails.getIsbn()).ifPresent(existingBook -> {
                    // Only throw if the existing book with this ISBN is different from the one we're updating
                    if (!existingBook.getId().equals(id)) {
                        throw new IllegalArgumentException("Book with ISBN " + bookDetails.getIsbn() + " already exists");
                    }
                });
            }
        }
        
        // Update book fields
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setPublicationDate(bookDetails.getPublicationDate());
        book.setPrice(bookDetails.getPrice());
        book.setDescription(bookDetails.getDescription());
        book.setPageCount(bookDetails.getPageCount());
        book.setPublisher(bookDetails.getPublisher());
        book.setGenre(bookDetails.getGenre());
        
        // Special handling for new books with pre-assigned IDs
        if (isNewBook) {
            // For new books with manual IDs, we need a specialized approach
            // First check that no book with this ID exists
            if (bookRepository.existsById(id)) {
                throw new IllegalArgumentException("Cannot create book with ID " + id + " as it already exists");
            }
            
            // Using the EntityManager directly to merge the entity with its ID
            // This approach preserves the manually assigned ID
            book = entityManager.merge(book);
            entityManager.flush(); // Ensure the entity is persisted immediately
            
            // Verify the ID was preserved
            if (!book.getId().equals(id)) {
                throw new IllegalStateException("Failed to preserve requested ID " + id + " when creating new book");
            }
            
            return book;
        } else {
            // Normal update for existing books
            return bookRepository.save(book);
        }
    }

    @Override
    @Transactional
    public boolean deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
        return true;
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> findBooksByGenre(Book.Genre genre) {
        return bookRepository.findByGenre(genre);
    }
}
