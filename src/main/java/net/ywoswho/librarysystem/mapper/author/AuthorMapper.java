package net.ywoswho.librarysystem.mapper.author;

import net.ywoswho.librarysystem.dto.author.AuthorDetailsDto;
import net.ywoswho.librarysystem.entity.Author;
import net.ywoswho.librarysystem.mapper.book.BookListItemMapper;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = BookListItemMapper.class
)
public interface AuthorMapper {
    AuthorDetailsDto toDetailsDto(Author author);
}
