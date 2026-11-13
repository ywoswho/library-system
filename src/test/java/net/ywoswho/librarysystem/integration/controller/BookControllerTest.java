package net.ywoswho.librarysystem.integration.controller;

import net.ywoswho.librarysystem.dto.book.CreateBookRequest;
import net.ywoswho.librarysystem.entity.Author;
import net.ywoswho.librarysystem.entity.Book;
import net.ywoswho.librarysystem.entity.Genre;
import net.ywoswho.librarysystem.integration.base.AbstractCrudIntegrationTest;
import net.ywoswho.librarysystem.repository.AuthorRepository;
import net.ywoswho.librarysystem.repository.BookRepository;
import net.ywoswho.librarysystem.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.ObjectMapper;

import java.util.Set;

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
}