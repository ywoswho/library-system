package net.ywoswho.librarysystem.service;

import net.ywoswho.librarysystem.dto.book.BookDetailsDto;
import net.ywoswho.librarysystem.dto.book.BookListItemDto;
import net.ywoswho.librarysystem.entity.Book;
import net.ywoswho.librarysystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookService(BookRepository bookRepository, AuthorService authorService, GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    public List<BookListItemDto> getAllBooks() {
        return bookRepository.findAll().stream().map(this::toBookListItemDto).toList();
    }

    private BookListItemDto toBookListItemDto(Book book) {
        return new BookListItemDto(
                book.getId(),
                book.getTitle(),
                book.getYearPublished()
        );
    }

    private BookDetailsDto toBookDetailsDto(Book book) {
        return new BookDetailsDto(
                book.getId(),
                book.getTitle(),
                book.getAuthors().stream().map(authorService::toAuthorListItemDto).toList(),
                book.getGenres().stream().map(genreService::toDto).toList(),
                book.getYearPublished()
        );
    }
}
