package net.ywoswho.librarysystem.dto.book;

import net.ywoswho.librarysystem.dto.genre.GenreDto;
import net.ywoswho.librarysystem.dto.author.AuthorListItemDto;

import java.util.List;

public record BookDetailsDto(
        Long id,
        String title,
        String longDescription,
        int yearPublished,
        List<AuthorListItemDto> authors,
        List<GenreDto> genres
) {
}
