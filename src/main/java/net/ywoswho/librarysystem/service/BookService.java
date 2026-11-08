package net.ywoswho.librarysystem.service;

import jakarta.persistence.EntityNotFoundException;
import net.ywoswho.librarysystem.dto.book.BookDetailsDto;
import net.ywoswho.librarysystem.dto.book.BookListItemDto;
import net.ywoswho.librarysystem.dto.book.CreateBookRequest;
import net.ywoswho.librarysystem.entity.Book;
import net.ywoswho.librarysystem.mapper.book.BookListItemMapper;
import net.ywoswho.librarysystem.mapper.book.BookMapper;
import net.ywoswho.librarysystem.repository.AuthorRepository;
import net.ywoswho.librarysystem.repository.BookRepository;
import net.ywoswho.librarysystem.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;
    private final BookListItemMapper bookListItemMapper;

    public BookService(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            GenreRepository genreRepository,
            BookMapper bookMapper,
            BookListItemMapper bookListItemMapper
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookMapper = bookMapper;
        this.bookListItemMapper = bookListItemMapper;
    }

    @Transactional(readOnly = true)
    public List<BookListItemDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookListItemMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookDetailsDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDetailsDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Book not found with id: " + id)
                );
    }

    @Transactional
    public BookDetailsDto createBook(CreateBookRequest request) {
        var book = new Book();
        book.setTitle(request.title());
        book.setYearPublished(request.yearPublished());
        book.setShortDescription(request.shortDescription());
        book.setLongDescription(request.longDescription());

        var authors = authorRepository.findAllById(
                request.authorIds()
        );
        var genres = genreRepository.findAllById(
                request.genreIds()
        );

        authors.forEach(book::addAuthor);
        genres.forEach(book::addGenre);

        return bookMapper.toDetailsDto(bookRepository.save(book));
    }

    @Transactional
    public BookDetailsDto updateBook(
            Long id,
            CreateBookRequest request
    ) {
        var book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Book not found with id: " + id)
                );

        var authors = authorRepository.findAllById(
                request.authorIds()
        );
        var genres = genreRepository.findAllById(
                request.genreIds()
        );

        book.setTitle(request.title());
        book.setYearPublished(request.yearPublished());

        new HashSet<>(book.getAuthors())
                .forEach(book::removeAuthor);
        authors.forEach(book::addAuthor);

        new HashSet<>(book.getGenres())
                .forEach(book::removeGenre);
        genres.forEach(book::addGenre);

        return bookMapper.toDetailsDto(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Book not found with id: " + id)
                );

        new HashSet<>(book.getAuthors())
                .forEach(book::removeAuthor);
        new HashSet<>(book.getGenres())
                .forEach(book::removeGenre);

        bookRepository.delete(book);
    }
}
