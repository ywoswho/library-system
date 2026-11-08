package net.ywoswho.librarysystem.service;

import jakarta.persistence.EntityNotFoundException;
import net.ywoswho.librarysystem.dto.user.UserDto;
import net.ywoswho.librarysystem.mapper.UserMapper;
import net.ywoswho.librarysystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
}
