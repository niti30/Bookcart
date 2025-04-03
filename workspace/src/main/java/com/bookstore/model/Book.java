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
}
