package com.lxvdnl.user.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "Name can't be empty")
    @Size(min = 1, max = 50, message = "Name size must be between 1 and 50")
    private String name;

    @NotBlank(message = "Username can't be empty")
    @Size(min = 1, max = 256, message = "Username size must be between 1 and 256")
    @Email(message = "Incorrect email")
    private String username;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 8, message = "Password must have at least 8 characters")
    @Pattern(regexp = ".*\\d.*", message = "Password must have at least 1 number")
    private String password;

}
