package com.lxvdnl.track.service;

import com.lxvdnl.track.exception.TrackNotFoundException;
import com.lxvdnl.track.exception.UserNotFoundException;
import com.lxvdnl.track.model.Track;
import com.lxvdnl.track.repository.TrackRepository;
import com.lxvdnl.track.web.dto.TrackDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final AudioFileUploadService audioFileUploadService;
    private final RestTemplate restTemplate;

    @Transactional
    public Track createTrack(UUID authorId, String title, MultipartFile audioFile) {
        try {
            restTemplate.getForObject("http://user-service:8080/api/v1/users/{authorId}", Object.class, authorId);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User with id " + authorId + " not found");
        }

        log.info("Download new file: {}", audioFile.getOriginalFilename());
        String url = audioFileUploadService.uploadFile(audioFile);
        log.info("File downloaded: {}", url);

        return trackRepository.save(Track.builder()
                .authorId(authorId)
                .title(title)
                .audioUrl(url)
                .build());
    }

    @Transactional(readOnly = true)
    public Track getTrackById(UUID trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow(() -> new TrackNotFoundException("Track with id " + trackId + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Track> getTracksByAuthorId(UUID authorId) {
        List<Track> tracks = trackRepository.findAllByAuthorId(authorId);
        if (tracks.isEmpty()) {
            throw new TrackNotFoundException("No tracks found for authorId: " + authorId);
        }
        return tracks;
    }

    @Transactional
    public Track updateTrack(UUID trackId, String newTitle) {
        return trackRepository.findById(trackId)
                .map(existedTrack -> {
                    existedTrack.setTitle(newTitle);
                    return trackRepository.save(existedTrack);
                })
                .orElseThrow(() -> new TrackNotFoundException("Track with id " + trackId + " not found"));
    }

    @Transactional
    public void deleteTrack(UUID trackId) {
        if (!trackRepository.existsById(trackId)) {
            throw new TrackNotFoundException("Track with id " + trackId + " not found");
        }

        trackRepository.deleteById(trackId);
    }

}
