package net.ywoswho.librarysystem.controller;

import jakarta.validation.Valid;
import net.ywoswho.librarysystem.dto.author.AuthorDetailsDto;
import net.ywoswho.librarysystem.dto.author.AuthorListItemDto;
import net.ywoswho.librarysystem.dto.author.CreateAuthorRequest;
import net.ywoswho.librarysystem.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping
    public List<AuthorListItemDto> getAllAuthors() {
        return service.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDetailsDto getAuthorById(
            @PathVariable Long id
    ) {
        return service.getAuthorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDetailsDto createAuthor(
            @RequestBody @Valid CreateAuthorRequest author
    ) {
        return service.createAuthor(author);
    }

    @PutMapping("/{id}")
    public AuthorDetailsDto updateAuthor(
            @PathVariable Long id,
            @RequestBody @Valid CreateAuthorRequest author
    ) {
        return service.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(
            @PathVariable Long id
    ) {
        service.deleteAuthor(id);
    }
}
