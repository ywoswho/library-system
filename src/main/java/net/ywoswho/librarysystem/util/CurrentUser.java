package net.ywoswho.librarysystem.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Objects;

@UtilityClass
public class CurrentUser {
    public long id(Jwt jwt) {
        return Long.parseLong(Objects.requireNonNull(jwt.getSubject()));
    }
}
