package net.ywoswho.librarysystem.integration.controller;

import com.jayway.jsonpath.JsonPath;
import net.ywoswho.librarysystem.entity.AppUser;
import net.ywoswho.librarysystem.entity.Book;
import net.ywoswho.librarysystem.entity.Rental;
import net.ywoswho.librarysystem.entity.RentalStatus;
import net.ywoswho.librarysystem.integration.base.AbstractIntegrationTest;
import net.ywoswho.librarysystem.repository.BookRepository;
import net.ywoswho.librarysystem.repository.RentalRepository;
import net.ywoswho.librarysystem.repository.UserRepository;
import net.ywoswho.librarysystem.security.WithMockAdmin;
import net.ywoswho.librarysystem.security.WithMockModerator;
import net.ywoswho.librarysystem.security.WithMockRegularUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RentalControllerTest extends AbstractIntegrationTest {
    protected Long existingBookId;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @BeforeEach
    void setUp() {
        userRepository.findByEmail("test@example.com")
                .orElseGet(() -> {
                    AppUser newUser = new AppUser();
                    newUser.setEmail("test@example.com");
                    newUser.setPasswordHash("password");
                    return userRepository.save(newUser);
                });

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setShortDescription("Clean coding principles");
        book.setLongDescription("A guide to writing clean code");
        book.setYearPublished(2008);
        existingBookId = bookRepository.save(book).getId();
    }

    @Test
    @WithMockAdmin
    void shouldReturnAllRentals() throws Exception {
        mvc.perform(get("/api/rentals"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockRegularUser
    void shouldForbidRegularUserFromGettingAllRentals() throws Exception {
        mvc.perform(get("/api/rentals"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockModerator
    void shouldForbidModeratorFromGettingAllRentals() throws Exception {
        mvc.perform(get("/api/rentals"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldRejectUnauthenticatedUserWhenGettingAllRentals() throws Exception {
        mvc.perform(get("/api/rentals"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockRegularUser
    void shouldCreateRental() throws Exception {
        mvc.perform(post("/api/rentals/rent/{id}", existingBookId))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockRegularUser
    void shouldRejectRentalWhenBookIsAlreadyRented() throws Exception {
        mvc.perform(post("/api/rentals/rent/{id}", existingBookId))
                .andExpect(status().isCreated());

        mvc.perform(post("/api/rentals/rent/{id}", existingBookId))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockRegularUser
    void shouldReturnNotFoundWhenBookDoesNotExist() throws Exception {
        mvc.perform(post("/api/rentals/rent/{id}", 999999))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldRejectUnauthenticatedUserWhenRentingBook() throws Exception {
        mvc.perform(post("/api/rentals/rent/{id}", existingBookId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockRegularUser
    void shouldReturnRental() throws Exception {
        var response = mvc.perform(post("/api/rentals/rent/{id}", existingBookId))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer id = JsonPath.read(response, "$.id");

        mvc.perform(put("/api/rentals/return/{id}", id))
                .andExpect(status().isOk());

        Rental rental = rentalRepository.findById(Long.valueOf(id)).orElseThrow();
        assertThat(rental.getStatus()).isEqualTo(RentalStatus.RETURNED);
    }

    @Test
    @WithMockRegularUser
    void shouldRejectReturnWhenRentalIsAlreadyReturned() throws Exception {
        var response = mvc.perform(post("/api/rentals/rent/{id}", existingBookId))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer id = JsonPath.read(response, "$.id");

        mvc.perform(put("/api/rentals/return/{id}", id))
                .andExpect(status().isOk());

        mvc.perform(put("/api/rentals/return/{id}", id))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldRejectUnauthenticatedUserWhenReturningRental() throws Exception {
        mvc.perform(put("/api/rentals/return/{id}", existingBookId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockRegularUser
    void shouldReturnNotFoundWhenRentalDoesNotExist() throws Exception {
        mvc.perform(put("/api/rentals/return/{id}", 999999))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockRegularUser
    void shouldReturnCurrentUserRentals() throws Exception {
        mvc.perform(get("/api/users/rentals"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectUnauthenticatedUserWhenGettingTheirRentals() throws Exception {
        mvc.perform(get("/api/users/rentals"))
                .andExpect(status().isUnauthorized());
    }
}
