package net.ywoswho.librarysystem.dto.author;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record CreateAuthorRequest(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        Set<Long> bookIds
) {
}
