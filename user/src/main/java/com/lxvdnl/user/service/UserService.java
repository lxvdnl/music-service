package com.lxvdnl.user.service;

import com.lxvdnl.user.exception.UserAlreadyExistsException;
import com.lxvdnl.user.exception.UserNotFoundException;
import com.lxvdnl.user.model.User;
import com.lxvdnl.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists");
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    }

    @Transactional
    public User updateUser(User user) {
        return userRepository.findById(user.getId())
                .map(existingUser -> {
                    if (!existingUser.getUsername().equals(user.getUsername())) {
                        userRepository.findByUsername(user.getUsername()).ifPresent(conflictUser -> {
                            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists");
                        });
                    }
                    existingUser.setName(user.getName());
                    existingUser.setUsername(user.getUsername());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException("User with ID " + user.getId() + " not found"));
    }

    @Transactional
    public void deleteUserById(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        userRepository.deleteById(userId);
    }

}