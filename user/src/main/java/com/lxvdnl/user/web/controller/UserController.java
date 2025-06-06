package com.lxvdnl.user.web.controller;

import com.lxvdnl.user.kafka.logging.KafkaLogProducer;
import com.lxvdnl.user.logging.AppLogger;
import com.lxvdnl.user.model.User;
import com.lxvdnl.user.service.UserService;
import com.lxvdnl.user.web.dto.UserDto;
import com.lxvdnl.user.web.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private static final AppLogger log = AppLogger.getLogger(UserController.class);
    private final KafkaLogProducer kafkaLogProducer;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Creating user: name={}, username={}", userDto.getName(), userDto.getUsername());
        User newUser = userService.createUser(userMapper.toEntity(userDto));
        log.info("Successfully created user: id={}, name={}, username={}", newUser.getId(), newUser.getName(), newUser.getUsername());

        String requestId = MDC.get("requestId");

        kafkaLogProducer.sendLog(
                "INFO",
                "User created: id=%s, username=%s".formatted(newUser.getId(), newUser.getUsername()),
                this.getClass().getName(),
                requestId
        );
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