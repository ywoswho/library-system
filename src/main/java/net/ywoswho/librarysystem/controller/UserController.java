package net.ywoswho.librarysystem.controller;

import net.ywoswho.librarysystem.dto.rental.RentalDto;
import net.ywoswho.librarysystem.dto.user.UserDto;
import net.ywoswho.librarysystem.service.RentalService;
import net.ywoswho.librarysystem.service.UserService;
import net.ywoswho.librarysystem.util.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final RentalService rentalService;

    public UserController(UserService userService, RentalService rentalService) {
        this.userService = userService;
        this.rentalService = rentalService;
    }

    @GetMapping("/me")
    public UserDto me (@AuthenticationPrincipal Jwt jwt) {
        return userService.getUserById(CurrentUser.id(jwt));
    }

    @GetMapping("/rentals")
    public List<RentalDto> getUserRentals(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return rentalService.getUserRentals(CurrentUser.id(jwt));
    }
}
