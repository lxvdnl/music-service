package com.lxvdnl.logging.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxvdnl.logging.kafka.dto.LogMessage;
import com.lxvdnl.logging.kafka.dto.mapper.LogMapper;
import com.lxvdnl.logging.repository.LoggingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaLogConsumer {

    private final ObjectMapper objectMapper;
    private final LoggingRepository loggingRepository;
    private final LogMapper logMapper;

    @KafkaListener(topics = "logs", groupId = "logging-group")
    public void consume(String message) {
        try {
            LogMessage logMessage = objectMapper.readValue(message, LogMessage.class);
            loggingRepository.save(logMapper.toEntity(logMessage));
            log.info("Saved log from Kafka: {}", logMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
