package com.lxvdnl.track.web.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackDto {

    @NotBlank(message = "Title can't be empty")
    @Size(min = 1, max = 50, message = "Title size must be between 1 and 50")
    private String title;

    @NotNull(message = "Audio file can't be null")
    @Max(value = 10 * 1024 * 1024, message = "Audio file size must be less than 10MB")
    @Pattern(regexp = ".*\\.(mp3|wav)$", message = "Invalid file format. Only mp3 and wav are allowed")
    private MultipartFile audioFile;

}
