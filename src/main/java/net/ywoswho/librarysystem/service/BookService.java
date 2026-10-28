package net.ywoswho.librarysystem.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import net.ywoswho.librarysystem.dto.book.BookDetailsDto;
import net.ywoswho.librarysystem.dto.book.BookListItemDto;
import net.ywoswho.librarysystem.dto.book.CreateBookRequest;
import net.ywoswho.librarysystem.entity.Book;
import net.ywoswho.librarysystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
        return bookRepository.findAll()
                .stream()
                .map(this::toBookListItemDto)
                .toList();
    }

    public BookDetailsDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::toBookDetailsDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Book not found with id: " + id)
                );
    }

    @Transactional
    public BookDetailsDto createBook(CreateBookRequest request) {
        var book = new Book();
        book.setTitle(request.title());
        book.setYearPublished(request.yearPublished());

        var authors = authorService.getAuthorsByIds(request.authorIds());
        var genres = genreService.getGenresByIds(request.genreIds());

        authors.forEach(book::addAuthor);
        genres.forEach(book::addGenre);

        return toBookDetailsDto(bookRepository.save(book));
    }

    @Transactional
    public BookDetailsDto updateBook(
            Long id,
            CreateBookRequest request
    ) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        var authors = authorService.getAuthorsByIds(request.authorIds());
        var genres = genreService.getGenresByIds(request.genreIds());

        book.setTitle(request.title());
        book.setYearPublished(request.yearPublished());

        new HashSet<>(book.getAuthors()).forEach(book::removeAuthor);
        authors.forEach(book::addAuthor);

        new HashSet<>(book.getGenres()).forEach(book::removeGenre);
        genres.forEach(book::addGenre);

        return toBookDetailsDto(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        new HashSet<>(book.getAuthors()).forEach(book::removeAuthor);
        new HashSet<>(book.getGenres()).forEach(book::removeGenre);

        bookRepository.deleteById(id);
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
                book.getYearPublished(),
                book.getAuthors().stream().map(authorService::toAuthorListItemDto).toList(),
                book.getGenres().stream().map(genreService::toDto).toList()
        );
    }
}
