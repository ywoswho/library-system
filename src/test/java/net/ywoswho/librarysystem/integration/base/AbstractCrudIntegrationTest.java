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
    void shouldGetAll() throws Exception {
        mvc.perform(get(baseUrl()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetById() throws Exception {
        mvc.perform(get(baseUrl() + "/{id}", existingId))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404ForMissingResourceOnGet() throws Exception {
        mvc.perform(get(baseUrl() + "/{id}", 999999))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockAdmin
    void shouldCreate() throws Exception {
        mvc.perform(post(baseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockModerator
    void shouldUpdate() throws Exception {
        mvc.perform(put(baseUrl() + "/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockAdmin
    void shouldReturn404ForMissingResourceOnUpdate() throws Exception {
        mvc.perform(put(baseUrl() + "/{id}", 999999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockAdmin
    void shouldDelete() throws Exception {
        mvc.perform(delete(baseUrl() + "/{id}", existingId))
                .andExpect(status().isNoContent());

        mvc.perform(get(baseUrl() + "/{id}", existingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockAdmin
    void shouldReturn404ForMissingResourceOnDelete() throws Exception {
        mvc.perform(delete(baseUrl() + "/{id}", 999999))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockAdmin
    void shouldReturn400WhenCreatePayloadIsInvalid() throws Exception {
        mvc.perform(post(baseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockModerator
    void shouldReturn400WhenUpdatePayloadIsInvalid() throws Exception {
        mvc.perform(put(baseUrl() + "/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockAdmin
    void shouldReturn400WhenJsonIsInvalid() throws Exception {
        mvc.perform(post(baseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("BAD &*& JSON"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenIdTypeIsInvalid() throws Exception {
        mvc.perform(get(baseUrl() + "/{id}", "invalid-id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn401WhenUnauthenticatedOnCreate() throws Exception {
        mvc.perform(post(baseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockRegularUser
    void shouldReturn403WhenRegularUserCreates() throws Exception {
        mvc.perform(post(baseUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload()))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn401WhenUnauthenticatedOnUpdate() throws Exception {
        mvc.perform(put(baseUrl() + "/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockRegularUser
    void shouldReturn403WhenRegularUserUpdates() throws Exception {
        mvc.perform(put(baseUrl() + "/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload()))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn401WhenUnauthenticatedOnDelete() throws Exception {
        mvc.perform(delete(baseUrl() + "/{id}", existingId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockRegularUser
    void shouldReturn403WhenRegularUserDeletes() throws Exception {
        mvc.perform(delete(baseUrl() + "/{id}", existingId))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockModerator
    void shouldReturn403WhenModeratorDeletes() throws Exception {
        mvc.perform(delete(baseUrl() + "/{id}", existingId))
                .andExpect(status().isForbidden());
    }
}