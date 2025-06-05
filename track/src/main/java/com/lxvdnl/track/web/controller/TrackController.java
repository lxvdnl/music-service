package com.lxvdnl.track.web.controller;

import com.lxvdnl.track.model.Track;
import com.lxvdnl.track.service.TrackService;
import com.lxvdnl.track.web.dto.TrackDto;
import com.lxvdnl.track.web.mapper.TrackMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;
    private final TrackMapper trackMapper;

    @PostMapping(path = "/{authorId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TrackDto> createTrack(
            @PathVariable("authorId") UUID authorId,
            @RequestParam("audioFile") MultipartFile audioFile,
            @RequestParam("title") String title) {

        log.info("New track publication: title={}, from user: id={}, file name={}", title, authorId, audioFile.getOriginalFilename());

        Track createdTrack = trackService.createTrack(authorId, title, audioFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(trackMapper.toDto(createdTrack));
    }

}
