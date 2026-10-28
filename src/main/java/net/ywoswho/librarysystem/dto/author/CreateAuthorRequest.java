package net.ywoswho.librarysystem.dto.author;

import java.util.Set;

public record CreateAuthorRequest(
        String firstName,
        String lastName,
        Set<Long> bookIds
) {
}
