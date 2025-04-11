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

import java.util.List;
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
        log.info("Successfully created track: id={}, title={}, from user: id={}", createdTrack.getId(), title, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(trackMapper.toDto(createdTrack));
    }

    @GetMapping("/{trackId}")
    public ResponseEntity<TrackDto> getTrack(@PathVariable("trackId") UUID trackId) {
        log.info("Get track: id={}", trackId);
        Track track = trackService.getTrackById(trackId);
        log.info("Successfully get track: id={}", trackId);
        return ResponseEntity.ok(trackMapper.toDto(track));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<TrackDto>> getTracksByAuthor(@PathVariable UUID authorId) {
        log.info("Get tracks by author: id={}", authorId);
        List<Track> tracks = trackService.getTracksByAuthorId(authorId);
        log.info("Successfully get tracks by author: id={}", authorId);
        return ResponseEntity.ok(trackMapper.toDtoList(tracks));
    }

    @PutMapping("/{trackId}")
    public ResponseEntity<TrackDto> updateTrack(
            @PathVariable("trackId") UUID trackId,
            @RequestParam("title") String newTitle) {
        log.info("Update track: id={}, title={}", trackId, newTitle);
        Track updatedTrack = trackService.updateTrack(trackId, newTitle);
        log.info("Successfully updated track: id={}, title={}", updatedTrack.getId(), updatedTrack.getTitle());
        return ResponseEntity.ok(trackMapper.toDto(updatedTrack));
    }

    @DeleteMapping("/{trackId}")
    public ResponseEntity<Void> deleteTrack(@PathVariable("trackId") UUID trackId) {
        log.info("Delete track: id={}", trackId);
        trackService.deleteTrack(trackId);
        log.info("Successfully deleted track: id={}", trackId);
        return ResponseEntity.noContent().build();
    }

}
