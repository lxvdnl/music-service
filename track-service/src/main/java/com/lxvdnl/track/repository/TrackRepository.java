package com.lxvdnl.track.repository;

import com.lxvdnl.track.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrackRepository extends JpaRepository<Track, UUID> {

    List<Track> findAllByAuthorId(UUID authorId);

}
