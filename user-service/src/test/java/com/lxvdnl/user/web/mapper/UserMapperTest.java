package com.lxvdnl.user.web.mapper;

import com.lxvdnl.user.model.User;
import com.lxvdnl.user.web.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void toEntity() {
        UserDto dto = UserDto.builder()
                .name("testName")
                .username("testUsername")
                .password("testPassword")
                .build();

        UserMapper userMapper = new UserMapper();
        User user = userMapper.toEntity(dto);

        assertNotNull(user);
        assertEquals("testName", user.getName());
        assertEquals("testUsername", user.getUsername());
        assertEquals("testPassword", user.getPassword());
    }

    @Test
    void toDto() {
        User user = User.builder()
                .name("testName")
                .username("testUsername")
                .password("testPassword")
                .build();

        UserMapper userMapper = new UserMapper();
        UserDto dto = userMapper.toDto(user);

        assertNotNull(dto);
        assertEquals("testName", dto.getName());
        assertEquals("testUsername", dto.getUsername());
        assertEquals("testPassword", dto.getPassword());
    }
}