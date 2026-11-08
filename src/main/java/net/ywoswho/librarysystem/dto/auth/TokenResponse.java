package net.ywoswho.librarysystem.dto.auth;

public record TokenResponse(
        String accessToken,
        long expiresInSeconds
) {
}
