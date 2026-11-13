package net.ywoswho.librarysystem.controller;

import jakarta.validation.Valid;
import net.ywoswho.librarysystem.dto.genre.CreateGenreRequest;
import net.ywoswho.librarysystem.dto.genre.GenreDto;
import net.ywoswho.librarysystem.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreDto> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public GenreDto getGenreById(
            @PathVariable("id") Long id
    ) {
        return genreService.getGenreById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto createGenre(
            @RequestBody @Valid CreateGenreRequest genre
    ) {
        return genreService.createGenre(genre);
    }

    @PutMapping("/{id}")
    public GenreDto updateGenre(
            @PathVariable("id") Long id,
            @RequestBody @Valid CreateGenreRequest request
    ) {
        return genreService.updateGenre(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(
            @PathVariable("id") Long id
    ) {
        genreService.deleteGenre(id);
    }
}
