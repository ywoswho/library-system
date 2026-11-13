package net.ywoswho.librarysystem.mapper.rental;

import net.ywoswho.librarysystem.dto.rental.RentalDto;
import net.ywoswho.librarysystem.entity.Rental;
import net.ywoswho.librarysystem.mapper.book.BookListItemMapper;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {
                BookListItemMapper.class
        }
)
public interface RentalMapper {
    RentalDto toDto(Rental rental);
}
