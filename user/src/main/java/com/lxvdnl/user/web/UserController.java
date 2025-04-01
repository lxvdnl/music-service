package com.lxvdnl.user.web;

import com.lxvdnl.user.model.User;
import com.lxvdnl.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        User newUser = userService.createUser(userMapper.toEntity(userDto));
        return ResponseEntity.ok(userMapper.toDto(newUser));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") UUID userId, @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setId(userId);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

}

// todo: validation
// todo: logging
