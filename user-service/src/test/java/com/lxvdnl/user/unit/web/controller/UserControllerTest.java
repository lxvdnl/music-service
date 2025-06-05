package com.lxvdnl.user.unit.web.controller;

import com.lxvdnl.user.model.User;
import com.lxvdnl.user.service.UserService;
import com.lxvdnl.user.web.controller.UserController;
import com.lxvdnl.user.web.dto.UserDto;
import com.lxvdnl.user.web.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser_shouldReturnCreatedUserDto() {

        UserDto dto = UserDto.builder()
                .name("testName")
                .username("testUsername")
                .password("testPassword")
                .build();

        User user = User.builder()
                .id(UUID.randomUUID())
                .name("testName")
                .username("testUsername")
                .password("testPassword")
                .build();



        when(userMapper.toEntity(dto)).thenReturn(user);
        when(userService.createUser(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(dto);

        ResponseEntity<UserDto> response = userController.createUser(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getUserById() {
        UUID userId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .name("testName")
                .username("testUsername")
                .password("testPassword")
                .build();

        UserDto dto = UserDto.builder()
                .name("testName")
                .username("testUsername")
                .password("testPassword")
                .build();

        when(userService.getUserById(userId)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(dto);

        ResponseEntity<UserDto> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateUser() {
        UUID userId = UUID.randomUUID();

        UserDto updatedDto = UserDto.builder()
                .name("updatedName")
                .username("updatedUsername")
                .password("updatedPassword")
                .build();

        User updatedUser = User.builder()
                .id(userId)
                .name("updatedName")
                .username("updatedUsername")
                .password("updatedPassword")
                .build();

        when(userMapper.toEntity(updatedDto)).thenReturn(updatedUser);
        when(userService.updateUser(updatedUser)).thenReturn(updatedUser);
        when(userMapper.toDto(updatedUser)).thenReturn(updatedDto);

        ResponseEntity<UserDto> response = userController.updateUser(userId, updatedDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());
    }

    @Test
    void deleteUser() {
        UUID userId = UUID.randomUUID();

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
