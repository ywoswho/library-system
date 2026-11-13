package net.ywoswho.librarysystem.service;

import jakarta.persistence.EntityNotFoundException;
import net.ywoswho.librarysystem.dto.rental.AdminRentalDto;
import net.ywoswho.librarysystem.dto.rental.RentalDto;
import net.ywoswho.librarysystem.entity.AppUser;
import net.ywoswho.librarysystem.entity.Book;
import net.ywoswho.librarysystem.entity.Rental;
import net.ywoswho.librarysystem.entity.RentalStatus;
import net.ywoswho.librarysystem.mapper.rental.AdminRentalMapper;
import net.ywoswho.librarysystem.mapper.rental.RentalMapper;
import net.ywoswho.librarysystem.repository.BookRepository;
import net.ywoswho.librarysystem.repository.RentalRepository;
import net.ywoswho.librarysystem.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final RentalMapper rentalMapper;
    private final AdminRentalMapper adminRentalMapper;

    public RentalService(
            RentalRepository rentalRepository,
            UserRepository userRepository,
            BookRepository bookRepository,
            RentalMapper rentalMapper, AdminRentalMapper adminRentalMapper
    ) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.rentalMapper = rentalMapper;
        this.adminRentalMapper = adminRentalMapper;
    }

    @Transactional(readOnly = true)
    public List<RentalDto> getUserRentals(Long userId) {
        return rentalRepository.findByUserIdOrderByRentalDateDesc(userId)
                .stream()
                .map(rentalMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AdminRentalDto> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(adminRentalMapper::toAdminDto)
                .toList();
    }

    @Transactional
    public RentalDto rentBook(Long userId, Long bookId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id: " + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Book not found with id: " + bookId));

        if (!isBookAvailable(bookId)) {
            throw new IllegalStateException("Book is already rented");
        }

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentalDate(LocalDate.now());
        rental.setDueDate(LocalDate.now().plusDays(14));
        rental.setStatus(RentalStatus.ACTIVE);

        return rentalMapper.toDto(rentalRepository.save(rental));
    }

    @Transactional
    public RentalDto returnBook(Long userId, Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Rental not found with id: " + rentalId));

        if (!rental.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("User is not the owner of the rental");
        }

        if (rental.getStatus().equals(RentalStatus.RETURNED)) {
            throw new IllegalStateException("Book is already returned");
        }

        rental.setReturnDate(LocalDate.now());
        rental.setStatus(RentalStatus.RETURNED);

        return rentalMapper.toDto(rental);
    }

    public boolean isBookAvailable(Long bookId) {
        return !rentalRepository.existsByBookIdAndStatus(bookId, RentalStatus.ACTIVE);
    }
}
