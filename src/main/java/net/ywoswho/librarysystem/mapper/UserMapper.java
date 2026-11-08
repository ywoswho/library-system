package net.ywoswho.librarysystem.mapper;

import net.ywoswho.librarysystem.dto.user.UserDto;
import net.ywoswho.librarysystem.entity.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(AppUser user);
}
