package com.lxvdnl.logging.kafka.dto.mapper;

import com.lxvdnl.logging.kafka.dto.LogMessage;
import com.lxvdnl.logging.model.Log;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LogMapper {

    public Log toEntity(LogMessage dto) {
        return Log.builder()
                .id(UUID.randomUUID())
                .level(dto.getLevel())
                .logger(dto.getLogger())
                .message(dto.getMessage())
                .requestId(dto.getRequestId())
                .service(dto.getService())
                .timestamp(dto.getTimestamp())
                .build();
    }

}
