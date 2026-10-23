package net.ywoswho.librarysystem.dto.author;

import net.ywoswho.librarysystem.dto.book.BookListItemDto;

import java.util.List;

public record AuthorDetailsDto(
        Long id,
        String firstName,
        String lastName,
        List<BookListItemDto> books
) {
}
