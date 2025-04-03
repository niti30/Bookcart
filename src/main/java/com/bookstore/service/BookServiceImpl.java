package com.bookstore.service;

import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
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
        Book book = getBookById(id);
        
        // If ISBN is changing, check it's not already used by another book
        if (!book.getIsbn().equals(bookDetails.getIsbn())) {
            bookRepository.findByIsbn(bookDetails.getIsbn()).ifPresent(existingBook -> {
                if (!existingBook.getId().equals(id)) {
                    throw new IllegalArgumentException("Book with ISBN " + bookDetails.getIsbn() + " already exists");
                }
            });
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
        
        return bookRepository.save(book);
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
