package net.ywoswho.librarysystem.repository;

import net.ywoswho.librarysystem.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
