package net.ywoswho.librarysystem.dto.book;

public record BookListItemDto(
        Long id,
        String title,
        int yearPublished
) {
}
