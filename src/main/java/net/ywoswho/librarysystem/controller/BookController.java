package net.ywoswho.librarysystem.controller;

import net.ywoswho.librarysystem.dto.book.BookListItemDto;
import net.ywoswho.librarysystem.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BookListItemDto>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAllBooks());
    }
}
