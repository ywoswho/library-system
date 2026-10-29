package net.ywoswho.librarysystem.service;

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

    @Transactional
    public GenreDto createGenre(CreateGenreRequest request) {
        var genre = new Genre();
        genre.setName(request.name());

        return genreMapper.toDto(genreRepository.save(genre));
    }
}
