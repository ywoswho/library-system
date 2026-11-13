package net.ywoswho.librarysystem.repository;

import net.ywoswho.librarysystem.entity.Rental;
import net.ywoswho.librarysystem.entity.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    boolean existsByBookIdAndStatus(Long bookId, RentalStatus status);
    List<Rental> findByUserIdOrderByRentalDateDesc(Long userId);
}
