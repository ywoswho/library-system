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
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorListItemDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDetailsDto getAuthorById(
            @PathVariable("id") Long id
    ) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDetailsDto createAuthor(
            @RequestBody @Valid CreateAuthorRequest author
    ) {
        return authorService.createAuthor(author);
    }

    @PutMapping("/{id}")
    public AuthorDetailsDto updateAuthor(
            @PathVariable("id") Long id,
            @RequestBody @Valid CreateAuthorRequest author
    ) {
        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(
            @PathVariable("id") Long id
    ) {
        authorService.deleteAuthor(id);
    }
}
