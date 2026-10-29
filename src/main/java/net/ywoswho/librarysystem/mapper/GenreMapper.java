package net.ywoswho.librarysystem.mapper;

import net.ywoswho.librarysystem.dto.genre.GenreDto;
import net.ywoswho.librarysystem.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDto toDto(Genre genre);
}
