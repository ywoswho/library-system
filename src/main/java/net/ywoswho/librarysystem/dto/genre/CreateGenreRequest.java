package net.ywoswho.librarysystem.dto.genre;

import jakarta.validation.constraints.NotBlank;

public record CreateGenreRequest (
        @NotBlank
        String name
) {
}
