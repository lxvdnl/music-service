package com.lxvdnl.user.service;

import com.lxvdnl.user.exception.UserAlreadyExistsException;
import com.lxvdnl.user.exception.UserNotFoundException;
import com.lxvdnl.user.model.User;
import com.lxvdnl.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldSaveUser_whenUsernameNotExists() {
        User user = User.builder()
                .username("testUsername")
                .name("testName")
                .password("testPassword")
                .creationDate(LocalDateTime.now())
                .id(UUID.randomUUID())
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.createUser(user);

        assertEquals("testUsername", savedUser.getUsername());
        verify(userRepository).save(user);

    }

    @Test
    void createUser_shouldThrowUserAlreadyExistsException_whenUsernameExists() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("testName")
                .username("testUsername")
                .creationDate(LocalDateTime.now())
                .password("testPassword")
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(user));

        verify(userRepository, never()).save(user);
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.of(User.builder().id(id).build()));

        User user = userService.getUserById(id);

        assertEquals(user.getId(), id);
    }

    @Test
    void getUserById_shouldThrowUserNotFoundException_whenUserNotExists() {
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser_whenUserExists() {
        UUID id = UUID.randomUUID();
        User existingUser = User.builder()
                .id(id)
                .username("oldUsername")
                .name("oldName")
                .password("oldPassword")
                .build();

        User updatedUser = User.builder()
                .id(id)
                .username("newUsername")
                .name("newName")
                .password("newPassword")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser(updatedUser);

        assertEquals("newUsername", result.getUsername());
        assertEquals("newName", result.getName());
        assertEquals("newPassword", result.getPassword());
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_shouldThrowUserNotFoundException_whenUserNotExists() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .username("username")
                .name("name")
                .password("password")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(user));
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUserById_shouldDeleteUser_whenUserExists() {
        UUID id = UUID.randomUUID();

        when(userRepository.existsById(id)).thenReturn(true);
        doNothing().when(userRepository).deleteById(id);

        userService.deleteUserById(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void deleteUserById_shouldThrowUserNotFoundException_whenUserNotExists() {
        UUID id = UUID.randomUUID();

        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(id));
        verify(userRepository, never()).deleteById(id);
    }
}
