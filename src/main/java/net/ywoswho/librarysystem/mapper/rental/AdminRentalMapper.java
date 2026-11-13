package net.ywoswho.librarysystem.mapper.rental;

import net.ywoswho.librarysystem.dto.rental.AdminRentalDto;
import net.ywoswho.librarysystem.entity.Rental;
import net.ywoswho.librarysystem.mapper.UserMapper;
import net.ywoswho.librarysystem.mapper.book.BookListItemMapper;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {
                UserMapper.class,
                BookListItemMapper.class
        }
)
public interface AdminRentalMapper {
    AdminRentalDto toAdminDto(Rental rental);
}
