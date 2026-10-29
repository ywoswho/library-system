package net.ywoswho.librarysystem.dto.book;

public record BookListItemDto(
        Long id,
        String title,
        String shortDescription,
        int yearPublished
) {
}
