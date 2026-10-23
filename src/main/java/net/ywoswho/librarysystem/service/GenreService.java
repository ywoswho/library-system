package net.ywoswho.librarysystem.service;

import net.ywoswho.librarysystem.dto.GenreDto;
import net.ywoswho.librarysystem.entity.Genre;
import net.ywoswho.librarysystem.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final GenreRepository repository;

    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    public List<GenreDto> getAllGenres() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
