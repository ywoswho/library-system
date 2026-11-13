package net.ywoswho.librarysystem.integration.controller;

import net.ywoswho.librarysystem.dto.genre.CreateGenreRequest;
import net.ywoswho.librarysystem.entity.Genre;
import net.ywoswho.librarysystem.integration.base.AbstractCrudIntegrationTest;
import net.ywoswho.librarysystem.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.ObjectMapper;

public class GenreControllerTest extends AbstractCrudIntegrationTest {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    protected String baseUrl() {
        return "/api/genres";
    }

    @Override
    protected Long createTestData() {
        Genre genre = new Genre();
        genre.setName("Science Fiction");
        return genreRepository.save(genre).getId();
    }

    @Override
    protected String createPayload() {
        return mapper.writeValueAsString(new CreateGenreRequest("Fantasy"));
    }

    @Override
    protected String updatePayload() {
        return mapper.writeValueAsString(new CreateGenreRequest("Updated Sci-Fi"));
    }

    @Override
    protected String invalidPayload() {
        return mapper.writeValueAsString(new CreateGenreRequest(""));
    }
}
