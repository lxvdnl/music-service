package com.lxvdnl.user.web.mapper;

import com.lxvdnl.user.model.User;
import com.lxvdnl.user.web.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .username(userDto.getUsername())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }

}
