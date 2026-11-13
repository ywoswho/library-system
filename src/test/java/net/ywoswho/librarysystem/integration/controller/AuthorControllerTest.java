package net.ywoswho.librarysystem.integration.controller;

import net.ywoswho.librarysystem.dto.author.CreateAuthorRequest;
import net.ywoswho.librarysystem.entity.Author;
import net.ywoswho.librarysystem.integration.base.AbstractCrudIntegrationTest;
import net.ywoswho.librarysystem.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;

public class AuthorControllerTest extends AbstractCrudIntegrationTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    protected String baseUrl() {
        return "/api/authors";
    }

    @Override
    protected Long createTestData() {
        Author author = new Author();
        author.setFirstName("Martin");
        author.setLastName("Fowler");
        return authorRepository.save(author).getId();
    }

    @Override
    protected String createPayload() {
        return mapper.writeValueAsString(
                new CreateAuthorRequest(
                        "Joshua",
                        "Bloch",
                        Collections.emptySet()
                )
        );
    }

    @Override
    protected String updatePayload() {
        return mapper.writeValueAsString(
                new CreateAuthorRequest(
                        "Martin",
                        "Updated Fowler",
                        Collections.emptySet()
                )
        );
    }
}
