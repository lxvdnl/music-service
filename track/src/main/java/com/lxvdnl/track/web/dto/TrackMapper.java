package com.lxvdnl.track.web.dto;

import com.lxvdnl.track.model.Track;
import org.springframework.stereotype.Component;

@Component
public class TrackMapper {

    public TrackDto toDto(Track track) {
        return TrackDto.builder()
                .title(track.getTitle())
                .authorId(track.getAuthorId())
                .build();
    }

    public Track toEntity(TrackDto trackDto) {
        return Track.builder()
                .title(trackDto.getTitle())
                .authorId(trackDto.getAuthorId())
                .build();
    }

}
