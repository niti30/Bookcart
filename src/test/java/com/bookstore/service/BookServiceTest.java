package com.bookstore.service;

import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

/**
 * Test class for BookService
 */
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book1;
    private Book book2;
    private List<Book> bookList;

    @BeforeEach
    void setUp() {
        book1 = Book.builder()
                .id(1L)
                .title("Test Book 1")
                .author("Test Author 1")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2022, 1, 1))
                .price(new BigDecimal("19.99"))
                .description("Test description 1")
                .pageCount(200)
                .publisher("Test Publisher")
                .genre(Book.Genre.FICTION)
                .build();

        book2 = Book.builder()
                .id(2L)
                .title("Test Book 2")
                .author("Test Author 2")
                .isbn("0987654321")
                .publicationDate(LocalDate.of(2021, 2, 2))
                .price(new BigDecimal("29.99"))
                .description("Test description 2")
                .pageCount(300)
                .publisher("Test Publisher")
                .genre(Book.Genre.SCIENCE_FICTION)
                .build();

        bookList = Arrays.asList(book1, book2);
    }

    @Test
    void getAllBooks_ShouldReturnAllBooks() {
        // given
        when(bookRepository.findAll()).thenReturn(bookList);

        // when
        List<Book> result = bookService.getAllBooks();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Book 1");
        assertThat(result.get(1).getTitle()).isEqualTo("Test Book 2");
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() {
        // given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        // when
        Book result = bookService.getBookById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Book 1");
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_ShouldThrowException_WhenBookDoesNotExist() {
        // given
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBookById(99L);
        });
        verify(bookRepository, times(1)).findById(99L);
    }

    @Test
    void getBookByIsbn_ShouldReturnBook_WhenBookExists() {
        // given
        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.of(book1));

        // when
        Book result = bookService.getBookByIsbn("1234567890");

        // then
        assertThat(result).isNotNull();
        assertThat(result.getIsbn()).isEqualTo("1234567890");
        assertThat(result.getTitle()).isEqualTo("Test Book 1");
        verify(bookRepository, times(1)).findByIsbn("1234567890");
    }

    @Test
    void getBookByIsbn_ShouldThrowException_WhenBookDoesNotExist() {
        // given
        when(bookRepository.findByIsbn("nonexistent")).thenReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBookByIsbn("nonexistent");
        });
        verify(bookRepository, times(1)).findByIsbn("nonexistent");
    }

    @Test
    void createBook_ShouldReturnSavedBook() {
        // given
        Book newBook = Book.builder()
                .title("New Book")
                .author("New Author")
                .isbn("9876543210")
                .publicationDate(LocalDate.of(2023, 3, 3))
                .price(new BigDecimal("39.99"))
                .description("New description")
                .pageCount(400)
                .publisher("New Publisher")
                .genre(Book.Genre.FANTASY)
                .build();

        Book savedBook = Book.builder()
                .id(3L)
                .title("New Book")
                .author("New Author")
                .isbn("9876543210")
                .publicationDate(LocalDate.of(2023, 3, 3))
                .price(new BigDecimal("39.99"))
                .description("New description")
                .pageCount(400)
                .publisher("New Publisher")
                .genre(Book.Genre.FANTASY)
                .build();

        when(bookRepository.findByIsbn("9876543210")).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // when
        Book result = bookService.createBook(newBook);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3L);
        assertThat(result.getTitle()).isEqualTo("New Book");
        verify(bookRepository, times(1)).findByIsbn("9876543210");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void createBook_ShouldThrowException_WhenIsbnAlreadyExists() {
        // given
        Book newBook = Book.builder()
                .title("New Book")
                .author("New Author")
                .isbn("1234567890")  // Same ISBN as book1
                .publicationDate(LocalDate.of(2023, 3, 3))
                .price(new BigDecimal("39.99"))
                .description("New description")
                .pageCount(400)
                .publisher("New Publisher")
                .genre(Book.Genre.FANTASY)
                .build();

        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.of(book1));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.createBook(newBook);
        });
        verify(bookRepository, times(1)).findByIsbn("1234567890");
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void updateBook_ShouldReturnUpdatedBook_WhenBookExists() {
        // given
        Book updatedDetails = Book.builder()
                .title("Updated Title")
                .author("Updated Author")
                .isbn("1234567890")  // Same ISBN
                .publicationDate(LocalDate.of(2022, 10, 10))
                .price(new BigDecimal("49.99"))
                .description("Updated description")
                .pageCount(250)
                .publisher("Updated Publisher")
                .genre(Book.Genre.MYSTERY)
                .build();

        Book updatedBook = Book.builder()
                .id(1L)
                .title("Updated Title")
                .author("Updated Author")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2022, 10, 10))
                .price(new BigDecimal("49.99"))
                .description("Updated description")
                .pageCount(250)
                .publisher("Updated Publisher")
                .genre(Book.Genre.MYSTERY)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // when
        Book result = bookService.updateBook(1L, updatedDetails);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Updated Title");
        assertThat(result.getAuthor()).isEqualTo("Updated Author");
        assertThat(result.getGenre()).isEqualTo(Book.Genre.MYSTERY);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBook_ShouldCreateNewBook_WhenBookDoesNotExist() {
        // given
        Book updatedDetails = Book.builder()
                .title("New Title")
                .author("New Author")
                .isbn("5555555555")  // New ISBN that doesn't exist
                .publicationDate(LocalDate.of(2022, 10, 10))
                .price(new BigDecimal("49.99"))
                .description("New description")
                .pageCount(250)
                .publisher("New Publisher")
                .genre(Book.Genre.MYSTERY)
                .build();

        Book newBook = Book.builder()
                .id(99L)
                .title("New Title")
                .author("New Author")
                .isbn("5555555555")
                .publicationDate(LocalDate.of(2022, 10, 10))
                .price(new BigDecimal("49.99"))
                .description("New description")
                .pageCount(250)
                .publisher("New Publisher")
                .genre(Book.Genre.MYSTERY)
                .build();

        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        // Only mock findByIsbn if the service implementation actually calls it
        // This will be used if the service checks for ISBN uniqueness
        lenient().when(bookRepository.findByIsbn("5555555555")).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(newBook);

        // when
        Book result = bookService.updateBook(99L, updatedDetails);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(99L);
        assertThat(result.getTitle()).isEqualTo("New Title");
        verify(bookRepository, times(1)).findById(99L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void deleteBook_ShouldReturnTrue_WhenBookExists() {
        // given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        doNothing().when(bookRepository).delete(any(Book.class));

        // when
        boolean result = bookService.deleteBook(1L);

        // then
        assertThat(result).isTrue();
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(any(Book.class));
    }

    @Test
    void deleteBook_ShouldThrowException_WhenBookDoesNotExist() {
        // given
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.deleteBook(99L);
        });
        verify(bookRepository, times(1)).findById(99L);
        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    void findBooksByAuthor_ShouldReturnBooks_WhenAuthorMatches() {
        // given
        when(bookRepository.findByAuthorContainingIgnoreCase("Test Author")).thenReturn(bookList);

        // when
        List<Book> result = bookService.findBooksByAuthor("Test Author");

        // then
        assertThat(result).hasSize(2);
        verify(bookRepository, times(1)).findByAuthorContainingIgnoreCase("Test Author");
    }

    @Test
    void findBooksByTitle_ShouldReturnBooks_WhenTitleMatches() {
        // given
        when(bookRepository.findByTitleContainingIgnoreCase("Test Book")).thenReturn(bookList);

        // when
        List<Book> result = bookService.findBooksByTitle("Test Book");

        // then
        assertThat(result).hasSize(2);
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("Test Book");
    }

    @Test
    void findBooksByGenre_ShouldReturnBooks_WhenGenreMatches() {
        // given
        when(bookRepository.findByGenre(Book.Genre.FICTION)).thenReturn(List.of(book1));

        // when
        List<Book> result = bookService.findBooksByGenre(Book.Genre.FICTION);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Book 1");
        verify(bookRepository, times(1)).findByGenre(Book.Genre.FICTION);
    }
}
