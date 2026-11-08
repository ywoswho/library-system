package net.ywoswho.librarysystem.mapper.book;

import net.ywoswho.librarysystem.dto.book.BookDetailsDto;
import net.ywoswho.librarysystem.entity.Book;
import net.ywoswho.librarysystem.mapper.GenreMapper;
import net.ywoswho.librarysystem.mapper.author.AuthorListItemMapper;
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
