package net.ywoswho.librarysystem.dto.rental;

import net.ywoswho.librarysystem.dto.book.BookListItemDto;
import net.ywoswho.librarysystem.entity.RentalStatus;

import java.time.LocalDate;

public record RentalDto(
        Long id,
        BookListItemDto book,
        LocalDate rentalDate,
        LocalDate dueDate,
        LocalDate returnDate,
        RentalStatus status
) {
}
