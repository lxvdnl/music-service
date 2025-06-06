package com.lxvdnl.logging.repository;

import com.lxvdnl.logging.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoggingRepository extends JpaRepository<Log, UUID> {
}
