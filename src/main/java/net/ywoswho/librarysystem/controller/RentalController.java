package net.ywoswho.librarysystem.controller;

import net.ywoswho.librarysystem.dto.rental.AdminRentalDto;
import net.ywoswho.librarysystem.dto.rental.RentalDto;
import net.ywoswho.librarysystem.service.RentalService;
import net.ywoswho.librarysystem.util.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public List<AdminRentalDto> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @PostMapping("/rent/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public RentalDto rentBook(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") Long id
    ) {
        return rentalService.rentBook(CurrentUser.id(jwt), id);
    }

    @PutMapping("/return/{id}")
    public RentalDto returnBook(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") Long id
    ) {
        return rentalService.returnBook(CurrentUser.id(jwt), id);
    }
}
