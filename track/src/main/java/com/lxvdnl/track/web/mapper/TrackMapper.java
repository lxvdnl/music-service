package com.lxvdnl.track.web.mapper;

import com.lxvdnl.track.model.Track;
import com.lxvdnl.track.web.dto.TrackDto;
import org.springframework.stereotype.Component;

@Component
public class TrackMapper {

    public TrackDto toDto(Track track) {
        return TrackDto.builder()
                .title(track.getTitle())
                .audioFileUrl(track.getAudioUrl())
                .build();
    }

    public Track toEntity(TrackDto trackDto) {
        return Track.builder()
                .title(trackDto.getTitle())
                .audioUrl(trackDto.getAudioFileUrl())
                .build();
    }

}
