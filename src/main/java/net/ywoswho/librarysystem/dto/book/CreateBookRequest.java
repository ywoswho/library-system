package net.ywoswho.librarysystem.dto.book;

public record CreateBookRequest(
        String title,
        Integer yearPublished
) {
}
