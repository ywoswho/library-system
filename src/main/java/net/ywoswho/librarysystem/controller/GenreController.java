package net.ywoswho.librarysystem.controller;

import jakarta.validation.Valid;
import net.ywoswho.librarysystem.dto.genre.CreateGenreRequest;
import net.ywoswho.librarysystem.dto.genre.GenreDto;
import net.ywoswho.librarysystem.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreDto> getAllGenres() {
        return genreService.getAllGenres();
    }

    @PostMapping
    public ResponseEntity<GenreDto> createGenre(
            @RequestBody @Valid CreateGenreRequest genre
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(genreService.createGenre(genre));
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(
            @PathVariable Long id
    ) {
        genreService.deleteGenre(id);
    }
}
