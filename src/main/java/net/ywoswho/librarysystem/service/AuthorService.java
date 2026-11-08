package net.ywoswho.librarysystem.service;

import jakarta.persistence.EntityNotFoundException;
import net.ywoswho.librarysystem.dto.author.AuthorDetailsDto;
import net.ywoswho.librarysystem.dto.author.AuthorListItemDto;
import net.ywoswho.librarysystem.dto.author.CreateAuthorRequest;
import net.ywoswho.librarysystem.entity.Author;
import net.ywoswho.librarysystem.mapper.author.AuthorListItemMapper;
import net.ywoswho.librarysystem.mapper.author.AuthorMapper;
import net.ywoswho.librarysystem.repository.AuthorRepository;
import net.ywoswho.librarysystem.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final AuthorListItemMapper authorListItemMapper;
    private final BookRepository bookRepository;

    public AuthorService(
            AuthorRepository authorRepository,
            AuthorMapper authorMapper,
            AuthorListItemMapper authorListItemMapper,
            BookRepository bookRepository
    ) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.authorListItemMapper = authorListItemMapper;
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<AuthorListItemDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(authorListItemMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AuthorDetailsDto getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDetailsDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Author not found with id: " + id)
                );
    }

    @Transactional
    public AuthorDetailsDto createAuthor(CreateAuthorRequest request) {
        var author = new Author();
        author.setFirstName(request.firstName());
        author.setLastName(request.lastName());

        var books = new HashSet<>(bookRepository.findAllById(
                request.bookIds()
        ));
        author.setBooks(books);

        return authorMapper.toDetailsDto(authorRepository.save(author));
    }

    @Transactional
    public AuthorDetailsDto updateAuthor(
            Long id,
            CreateAuthorRequest request
    ) {
        var author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Author not found with id: " + id)
                );

        var books = new HashSet<>(bookRepository.findAllById(
                request.bookIds()
        ));

        author.setFirstName(request.firstName());
        author.setLastName(request.lastName());
        author.setBooks(books);

        return authorMapper.toDetailsDto(author);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Author not found with id: " + id)
                );
        author.getBooks().clear();
        authorRepository.delete(author);
    }
}
