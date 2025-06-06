package com.lxvdnl.user.kafka.logging.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LogMessage {
    private String level;
    private String service;
    private String logger;
    private String message;
    private String requestId;
    private LocalDateTime timestamp;
}
