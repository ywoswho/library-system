package net.ywoswho.librarysystem.mapper.author;

import net.ywoswho.librarysystem.dto.author.AuthorListItemDto;
import net.ywoswho.librarysystem.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorListItemMapper {
    AuthorListItemDto toDto(Author author);
}
