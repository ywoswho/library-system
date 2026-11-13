package net.ywoswho.librarysystem.service;

import jakarta.persistence.EntityNotFoundException;
import net.ywoswho.librarysystem.dto.genre.CreateGenreRequest;
import net.ywoswho.librarysystem.dto.genre.GenreDto;
import net.ywoswho.librarysystem.entity.Genre;
import net.ywoswho.librarysystem.mapper.GenreMapper;
import net.ywoswho.librarysystem.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreService(
            GenreRepository repository,
            GenreMapper genreMapper
    ) {
        this.genreRepository = repository;
        this.genreMapper = genreMapper;
    }

    @Transactional(readOnly = true)
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(genreMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public GenreDto getGenreById(Long id) {
        return genreRepository.findById(id)
                .map(genreMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Genre not found with id: " + id)
                );
    }

    @Transactional
    public GenreDto createGenre(CreateGenreRequest request) {
        var genre = new Genre();
        genre.setName(request.name());

        return genreMapper.toDto(genreRepository.save(genre));
    }

    @Transactional
    public GenreDto updateGenre(
            Long id,
            CreateGenreRequest request
    ) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Genre not found with id: " + id)
                );

        genre.setName(request.name());

        return genreMapper.toDto(genre);
    }

    @Transactional
    public void deleteGenre(Long id) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Genre not found with id: " + id)
                );
        genreRepository.delete(genre);
    }
}
