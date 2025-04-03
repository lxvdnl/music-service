package com.lxvdnl.track.service;

import com.lxvdnl.track.model.Track;
import com.lxvdnl.track.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final AudioFileUploadService audioFileUploadService;

    public Track createTrack(UUID authorId, String title, MultipartFile audioFile) {
        // todo: do check for valid authorId from UserService

        String url = audioFileUploadService.uploadFile(audioFile);

        return trackRepository.save(Track.builder()
                .authorId(authorId)
                .title(title)
                .audioUrl(url)
                .build());
    }

}
