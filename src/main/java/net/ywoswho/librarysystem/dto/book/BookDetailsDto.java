package net.ywoswho.librarysystem.dto.book;

import net.ywoswho.librarysystem.dto.GenreDto;
import net.ywoswho.librarysystem.dto.author.AuthorListItemDto;

import java.util.List;

public record BookDetailsDto(
        Long id,
        String title,
        List<AuthorListItemDto> authors,
        List<GenreDto> genres,
        int yearPublished
) {
}
