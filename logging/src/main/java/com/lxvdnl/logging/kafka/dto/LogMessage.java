package com.lxvdnl.logging.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogMessage {
    private String level;
    private String service;
    private String logger;
    private String message;
    private String requestId;
    private LocalDateTime timestamp;
}