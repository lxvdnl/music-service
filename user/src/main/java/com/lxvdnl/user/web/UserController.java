package com.lxvdnl.user.web;

import com.lxvdnl.user.model.User;
import com.lxvdnl.user.service.UserService;
import com.lxvdnl.user.web.dto.UserDto;
import com.lxvdnl.user.web.dto.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Creating user: name={}, username={}", userDto.getName(), userDto.getUsername());
        User newUser = userService.createUser(userMapper.toEntity(userDto));
        log.info("Successfully created user: id={}, name={}, username={}", newUser.getId(), newUser.getName(), newUser.getUsername());
        return ResponseEntity.ok(userMapper.toDto(newUser));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") UUID userId) {
        log.info("Getting user by id: {}", userId);
        User user = userService.getUserById(userId);
        log.info("Successfully get user by id: id={}, name={}, username={}", user.getId(), user.getName(), user.getUsername());
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") UUID userId, @Valid @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setId(userId);
        log.info("Updating user: id={}, name={}, username={}", user.getId(), user.getName(), user.getUsername());
        User updatedUser = userService.updateUser(user);
        log.info("Successfully updated user: id={}, name={}, username={}", updatedUser.getId(), updatedUser.getName(), updatedUser.getUsername());
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
        log.info("Deleting user by id: {}", userId);
        userService.deleteUserById(userId);
        log.info("Successfully deleted user by id: {}", userId);
        return ResponseEntity.noContent().build();
    }

}