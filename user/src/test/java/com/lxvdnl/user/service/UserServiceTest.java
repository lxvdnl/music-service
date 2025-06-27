package com.lxvdnl.user.service;

import com.lxvdnl.user.model.User;
import com.lxvdnl.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldSaveUser_whenUsernameNotExists() {
        User user = User.builder()
                .username("testUsername")
                .name("testName")
                .creationDate(LocalDateTime.now())
                .id(UUID.randomUUID())
                .build();

        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.createUser(user);

        assertEquals("testUsername", savedUser.getUsername());
        verify(userRepository).save(user);

    }

}