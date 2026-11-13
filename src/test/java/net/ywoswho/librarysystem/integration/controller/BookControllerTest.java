package net.ywoswho.librarysystem.integration.controller;

import net.ywoswho.librarysystem.dto.book.CreateBookRequest;
import net.ywoswho.librarysystem.entity.Author;
import net.ywoswho.librarysystem.entity.Book;
import net.ywoswho.librarysystem.entity.Genre;
import net.ywoswho.librarysystem.integration.base.AbstractCrudIntegrationTest;
import net.ywoswho.librarysystem.repository.AuthorRepository;
import net.ywoswho.librarysystem.repository.BookRepository;
import net.ywoswho.librarysystem.repository.GenreRepository;
import net.ywoswho.librarysystem.security.WithMockAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends AbstractCrudIntegrationTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    protected String baseUrl() {
        return "/api/books";
    }

    private Long authorId;
    private Long genreId;

    @Override
    protected Long createTestData() {
        Author author = new Author();
        author.setFirstName("Robert");
        author.setLastName("Martin");
        authorId = authorRepository.save(author).getId();

        Genre genre = new Genre();
        genre.setName("Programming");
        genreId = genreRepository.save(genre).getId();

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setShortDescription("Clean coding principles");
        book.setLongDescription("A guide to writing clean code");
        book.setYearPublished(2008);

        book.addAuthor(author);
        book.addGenre(genre);

        return bookRepository.save(book).getId();
    }

    @Override
    protected String createPayload() {
        return mapper.writeValueAsString(
                new CreateBookRequest(
                        "Effective Java",
                        "Java best practices",
                        "A guide to effective Java programming",
                        2018,
                        Set.of(authorId),
                        Set.of(genreId)
                )
        );
    }

    @Override
    protected String updatePayload() {
        return mapper.writeValueAsString(
                new CreateBookRequest(
                        "Updated Clean Code",
                        "Updated description",
                        "Updated long description",
                        2009,
                        Set.of(authorId),
                        Set.of(genreId)
                )
        );
    }

    @Override
    protected String invalidPayload() {
        return mapper.writeValueAsString(
                new CreateBookRequest(
                        "Effective Java",
                        "",
                        "",
                        2009,
                        Set.of(authorId),
                        Set.of(genreId)
                )
        );
    }

    @Test
    @WithMockAdmin
    void shouldReturn400WhenBookTitleIsBlank() throws Exception {
        String invalidBook = mapper.writeValueAsString(
                new CreateBookRequest(
                        "",
                        "Short desc",
                        "Long desc",
                        2024,
                        Set.of(authorId),
                        Set.of(genreId)
                )
        );

        mvc.perform(post(baseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBook))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockAdmin
    void shouldReturn400WhenAuthorIdsAreNull() throws Exception {
        String invalidBook = mapper.writeValueAsString(
                new CreateBookRequest(
                        "Clean Architecture",
                        "Desc",
                        "Long desc",
                        2024,
                        null,
                        Set.of(genreId)
                )
        );

        mvc.perform(post(baseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBook))
                .andExpect(status().isBadRequest());
    }
}