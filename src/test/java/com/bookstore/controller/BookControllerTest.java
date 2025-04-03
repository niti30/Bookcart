package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for BookController
 */
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book1;
    private Book book2;
    private List<Book> books;

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

        books = Arrays.asList(book1, book2);
    }

    @Test
    void getAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test Book 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Test Book 2")));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(book1);

        mockMvc.perform(get("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Book 1")))
                .andExpect(jsonPath("$.author", is("Test Author 1")))
                .andExpect(jsonPath("$.isbn", is("1234567890")));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void getBookByIsbn() throws Exception {
        when(bookService.getBookByIsbn("1234567890")).thenReturn(book1);

        mockMvc.perform(get("/api/books/isbn/1234567890")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Book 1")))
                .andExpect(jsonPath("$.isbn", is("1234567890")));

        verify(bookService, times(1)).getBookByIsbn("1234567890");
    }

    @Test
    void createBook() throws Exception {
        when(bookService.createBook(any(Book.class))).thenReturn(book1);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Book 1")));

        verify(bookService, times(1)).createBook(any(Book.class));
    }

    @Test
    void updateBook() throws Exception {
        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(book1);

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Book 1")));

        verify(bookService, times(1)).updateBook(anyLong(), any(Book.class));
    }

    @Test
    void deleteBook() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted", is(true)));

        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void findBooksByAuthor() throws Exception {
        when(bookService.findBooksByAuthor("Test Author")).thenReturn(books);

        mockMvc.perform(get("/api/books/search/author")
                .param("author", "Test Author")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(bookService, times(1)).findBooksByAuthor("Test Author");
    }

    @Test
    void findBooksByTitle() throws Exception {
        when(bookService.findBooksByTitle("Test Book")).thenReturn(books);

        mockMvc.perform(get("/api/books/search/title")
                .param("title", "Test Book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(bookService, times(1)).findBooksByTitle("Test Book");
    }

    @Test
    void findBooksByGenre() throws Exception {
        when(bookService.findBooksByGenre(Book.Genre.FICTION)).thenReturn(List.of(book1));

        mockMvc.perform(get("/api/books/search/genre")
                .param("genre", "FICTION")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].genre", is("FICTION")));

        verify(bookService, times(1)).findBooksByGenre(Book.Genre.FICTION);
    }
}
