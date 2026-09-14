package com.example.eventmanager.service;

import com.example.eventmanager.exception.BadRequestException;
import com.example.eventmanager.exception.ResourceNotFoundException;
import com.example.eventmanager.model.User;
import com.example.eventmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email already in use: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User details) {
        User user = getUserById(id);

        if (!user.getEmail().equals(details.getEmail()) && userRepository.existsByEmail(details.getEmail())) {
            throw new BadRequestException("Email already in use: " + details.getEmail());
        }

        user.setName(details.getName());
        user.setEmail(details.getEmail());
        if (details.getPassword() != null && !details.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(details.getPassword()));
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
