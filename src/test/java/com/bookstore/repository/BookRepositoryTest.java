package com.bookstore.repository;

import com.bookstore.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for BookRepository
 */
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findByIsbn_ShouldReturnBook_WhenIsbnExists() {
        // given
        Book book = createTestBook();
        entityManager.persist(book);
        entityManager.flush();

        // when
        Optional<Book> found = bookRepository.findByIsbn(book.getIsbn());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void findByIsbn_ShouldReturnEmpty_WhenIsbnDoesNotExist() {
        // when
        Optional<Book> found = bookRepository.findByIsbn("nonexistent");

        // then
        assertThat(found).isEmpty();
    }

    @Test
    void findByAuthorContainingIgnoreCase_ShouldReturnBooks_WhenAuthorMatches() {
        // given
        Book book1 = createTestBook();
        Book book2 = createAnotherTestBook();
        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.flush();

        // when
        List<Book> foundBooks = bookRepository.findByAuthorContainingIgnoreCase("author");

        // then
        assertThat(foundBooks).hasSize(2);
    }

    @Test
    void findByTitleContainingIgnoreCase_ShouldReturnBooks_WhenTitleMatches() {
        // given
        Book book1 = createTestBook();
        Book book2 = createAnotherTestBook();
        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.flush();

        // when
        List<Book> foundBooks = bookRepository.findByTitleContainingIgnoreCase("book");

        // then
        assertThat(foundBooks).hasSize(2);
    }

    @Test
    void findByGenre_ShouldReturnBooks_WhenGenreMatches() {
        // given
        Book book1 = createTestBook();
        Book book2 = createAnotherTestBook();
        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.flush();

        // when
        List<Book> foundBooks = bookRepository.findByGenre(Book.Genre.FICTION);

        // then
        assertThat(foundBooks).hasSize(1);
        assertThat(foundBooks.get(0).getTitle()).isEqualTo(book1.getTitle());
    }

    /**
     * Helper method to create a test book
     */
    private Book createTestBook() {
        return Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2022, 1, 1))
                .price(new BigDecimal("19.99"))
                .description("Test description")
                .pageCount(200)
                .publisher("Test Publisher")
                .genre(Book.Genre.FICTION)
                .build();
    }

    /**
     * Helper method to create another test book with different properties
     */
    private Book createAnotherTestBook() {
        return Book.builder()
                .title("Another Book")
                .author("Another Author")
                .isbn("0987654321")
                .publicationDate(LocalDate.of(2021, 2, 2))
                .price(new BigDecimal("29.99"))
                .description("Another description")
                .pageCount(300)
                .publisher("Another Publisher")
                .genre(Book.Genre.SCIENCE_FICTION)
                .build();
    }
}
