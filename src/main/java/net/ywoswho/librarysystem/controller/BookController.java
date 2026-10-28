package net.ywoswho.librarysystem.controller;

import jakarta.validation.Valid;
import net.ywoswho.librarysystem.dto.book.BookDetailsDto;
import net.ywoswho.librarysystem.dto.book.BookListItemDto;
import net.ywoswho.librarysystem.dto.book.CreateBookRequest;
import net.ywoswho.librarysystem.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
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
            @PathVariable Long id
    ) {
        return service.getBookById(id);
    }

    @PostMapping
    public ResponseEntity<BookDetailsDto> createBook(
            @RequestBody @Valid CreateBookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createBook(request));
    }

    @PutMapping("/{id}")
    public BookDetailsDto updateBook(
            @PathVariable Long id,
            @RequestBody @Valid CreateBookRequest request
    ) {
        return service.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(
            @PathVariable Long id
    ) {
        service.deleteBook(id);
    }
}
