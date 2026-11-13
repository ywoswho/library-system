package net.ywoswho.librarysystem.controller;

import jakarta.validation.Valid;
import net.ywoswho.librarysystem.dto.book.BookDetailsDto;
import net.ywoswho.librarysystem.dto.book.BookListItemDto;
import net.ywoswho.librarysystem.dto.book.CreateBookRequest;
import net.ywoswho.librarysystem.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<BookListItemDto> getAllBooks() {
        return service.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDetailsDto getBookById(
            @PathVariable("id") Long id
    ) {
        return service.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDetailsDto createBook(
            @RequestBody @Valid CreateBookRequest request
    ) {
        return service.createBook(request);
    }

    @PutMapping("/{id}")
    public BookDetailsDto updateBook(
            @PathVariable("id") Long id,
            @RequestBody @Valid CreateBookRequest request
    ) {
        return service.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(
            @PathVariable("id") Long id
    ) {
        service.deleteBook(id);
    }
}
