package net.ywoswho.librarysystem.mapper;

import net.ywoswho.librarysystem.dto.book.BookDetailsDto;
import net.ywoswho.librarysystem.entity.Book;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {
                AuthorListItemMapper.class,
                GenreMapper.class
        }
)
public interface BookMapper {
    BookDetailsDto toDetailsDto(Book book);
}
