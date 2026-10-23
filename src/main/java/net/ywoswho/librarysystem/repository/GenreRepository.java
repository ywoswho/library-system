package net.ywoswho.librarysystem.repository;

import net.ywoswho.librarysystem.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
