package net.ywoswho.librarysystem.mapper;

import net.ywoswho.librarysystem.dto.book.BookListItemDto;
import net.ywoswho.librarysystem.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookListItemMapper {
    BookListItemDto toDto(Book book);
}
