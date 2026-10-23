package net.ywoswho.librarysystem.service;

import net.ywoswho.librarysystem.dto.author.AuthorListItemDto;
import net.ywoswho.librarysystem.entity.Author;
import net.ywoswho.librarysystem.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public List<AuthorListItemDto> getAllAuthors() {
        return repository.findAll().stream().map(this::toAuthorListItemDto).toList();
    }

    public AuthorListItemDto toAuthorListItemDto(Author author) {
        return new AuthorListItemDto(
                author.getId(),
                author.getFirstName(),
                author.getLastName()
        );
    }
}
