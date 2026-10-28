package net.ywoswho.librarysystem.mapper;

import net.ywoswho.librarysystem.dto.author.AuthorDetailsDto;
import net.ywoswho.librarysystem.entity.Author;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = BookListItemMapper.class
)
public interface AuthorMapper {
    AuthorDetailsDto toDetailsDto(Author author);
}
