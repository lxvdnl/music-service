package com.lxvdnl.track;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final TrackMapper trackMapper;

    public Track createTrack(TrackDto trackDto) {
        // todo: check for availability in db
        log.info("New user registration {}", trackDto);

        Track track = trackMapper.toEntity(trackDto);

        // todo: upload audio file to s3 and return url for track
        // plug
        track.setAudioUrl("plug");

        return trackRepository.save(track);
    }

}
