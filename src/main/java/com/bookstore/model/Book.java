package com.bookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Book entity representing a book in the bookstore
 */
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 255, message = "Author name cannot exceed 255 characters")
    @Column(nullable = false)
    private String author;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    @Column(nullable = false, unique = true)
    private String isbn;

    @NotNull(message = "Publication date is required")
    @PastOrPresent(message = "Publication date cannot be in the future")
    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(nullable = false)
    private BigDecimal price;

    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    @Column(length = 5000)
    private String description;

    @NotNull(message = "Page count is required")
    @Column(name = "page_count", nullable = false)
    private Integer pageCount;

    @NotBlank(message = "Publisher is required")
    @Size(max = 255, message = "Publisher name cannot exceed 255 characters")
    @Column(nullable = false)
    private String publisher;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    /**
     * Enum representing book genres
     */
    public enum Genre {
        FICTION, 
        NON_FICTION, 
        SCIENCE_FICTION, 
        FANTASY, 
        MYSTERY, 
        THRILLER, 
        ROMANCE, 
        WESTERN, 
        HORROR, 
        BIOGRAPHY, 
        HISTORY, 
        SCIENCE, 
        POETRY, 
        CHILDREN,
        YOUNG_ADULT,
        SELF_HELP,
        BUSINESS,
        TRAVEL,
        COOKING,
        ART,
        EDUCATION,
        REFERENCE,
        TECHNOLOGY,
        OTHER
    }
    
    // Manually added getter and setter methods to work around Lombok issues
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    
    // Manual implementation of builder for Lombok @Builder
    public static BookBuilder builder() {
        return new BookBuilder();
    }
    
    public static class BookBuilder {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private LocalDate publicationDate;
        private BigDecimal price;
        private String description;
        private Integer pageCount;
        private String publisher;
        private Genre genre;
        
        BookBuilder() {
        }
        
        public BookBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }
        
        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }
        
        public BookBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }
        
        public BookBuilder publicationDate(LocalDate publicationDate) {
            this.publicationDate = publicationDate;
            return this;
        }
        
        public BookBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }
        
        public BookBuilder description(String description) {
            this.description = description;
            return this;
        }
        
        public BookBuilder pageCount(Integer pageCount) {
            this.pageCount = pageCount;
            return this;
        }
        
        public BookBuilder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }
        
        public BookBuilder genre(Genre genre) {
            this.genre = genre;
            return this;
        }
        
        public Book build() {
            return new Book(id, title, author, isbn, publicationDate, price, description, pageCount, publisher, genre);
        }
        
        public String toString() {
            return "Book.BookBuilder(id=" + this.id + ", title=" + this.title + ", author=" + this.author + ", isbn=" + this.isbn + ", publicationDate=" + this.publicationDate + ", price=" + this.price + ", description=" + this.description + ", pageCount=" + this.pageCount + ", publisher=" + this.publisher + ", genre=" + this.genre + ")";
        }
    }
}
