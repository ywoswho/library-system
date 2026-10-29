package net.ywoswho.librarysystem.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateBookRequest(
        @NotBlank
        String title,
        @NotBlank
        String shortDescription,
        @NotBlank
        String longDescription,
        @NotNull
        Integer yearPublished,
        @NotNull
        Set<Long> authorIds,
        @NotNull
        Set<Long> genreIds
) {
}
