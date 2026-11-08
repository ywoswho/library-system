package net.ywoswho.librarysystem.controller;

import jakarta.validation.Valid;
import net.ywoswho.librarysystem.dto.auth.LoginRequest;
import net.ywoswho.librarysystem.dto.auth.RegisterRequest;
import net.ywoswho.librarysystem.dto.auth.TokenResponse;
import net.ywoswho.librarysystem.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @RequestBody @Valid RegisterRequest request
    ) {
        authService.register(request);
    }

    @PostMapping("/login")
    public TokenResponse login(
            @RequestBody @Valid LoginRequest request
    ) {
        return authService.login(request);
    }
}
