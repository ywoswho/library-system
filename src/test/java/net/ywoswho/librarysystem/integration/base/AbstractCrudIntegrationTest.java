package net.ywoswho.librarysystem.integration.base;

import net.ywoswho.librarysystem.security.WithMockAdmin;
import net.ywoswho.librarysystem.security.WithMockModerator;
import net.ywoswho.librarysystem.security.WithMockRegularUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractCrudIntegrationTest extends AbstractIntegrationTest {
    protected Long existingId;

    protected abstract String baseUrl();
    protected abstract Long createTestData();
    protected abstract String createPayload();
    protected abstract String updatePayload();
    protected abstract String invalidPayload();

    @BeforeEach
    void setUp() {
        existingId = createTestData();
    }

    @Test
    void shouldReturnAll() throws Exception {
        mvc.perform(get(baseUrl()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnById() throws Exception {
        mvc.perform(get(baseUrl() + "/{id}", existingId))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenResourceDoesNotExist() throws Exception {
        mvc.perform(get(baseUrl() + "/{id}", 999999))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockAdmin
    void shouldCreateResource() throws Exception {
        mvc.perform(post(baseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockModerator
    void shouldUpdateResource() throws Exception {
        mvc.perform(put(baseUrl() + "/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockAdmin
    void shouldReturnNotFoundWhenUpdatingNonexistentResource() throws Exception {
        mvc.perform(put(baseUrl() + "/{id}", 999999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockAdmin
    void shouldDeleteResource() throws Exception {
        mvc.perform(delete(baseUrl() + "/{id}", existingId))
                .andExpect(status().isNoContent());

        mvc.perform(get(baseUrl() + "/{id}", existingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockAdmin
    void shouldReturnNotFoundWhenDeletingNonexistentResource() throws Exception {
        mvc.perform(delete(baseUrl() + "/{id}", 999999))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockAdmin
    void shouldRejectCreationWhenPayloadIsInvalid() throws Exception {
        mvc.perform(post(baseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockModerator
    void shouldRejectUpdateWhenPayloadIsInvalid() throws Exception {
        mvc.perform(put(baseUrl() + "/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockAdmin
    void shouldRejectCreationWhenJsonIsMalformed() throws Exception {
        mvc.perform(post(baseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("BAD &*& JSON"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectRequestWhenIdFormatIsInvalid() throws Exception {
        mvc.perform(get(baseUrl() + "/{id}", "invalid-id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectUnauthenticatedUserWhenCreatingResource() throws Exception {
        mvc.perform(post(baseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockRegularUser
    void shouldForbidRegularUserFromCreatingResource() throws Exception {
        mvc.perform(post(baseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload()))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldRejectUnauthenticatedUserWhenUpdatingResource() throws Exception {
        mvc.perform(put(baseUrl() + "/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockRegularUser
    void shouldForbidRegularUserFromUpdatingResource() throws Exception {
        mvc.perform(put(baseUrl() + "/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload()))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldRejectUnauthenticatedUserWhenDeletingResource() throws Exception {
        mvc.perform(delete(baseUrl() + "/{id}", existingId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockRegularUser
    void shouldForbidRegularUserFromDeletingResource() throws Exception {
        mvc.perform(delete(baseUrl() + "/{id}", existingId))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockModerator
    void shouldForbidModeratorFromDeletingResource() throws Exception {
        mvc.perform(delete(baseUrl() + "/{id}", existingId))
                .andExpect(status().isForbidden());
    }
}