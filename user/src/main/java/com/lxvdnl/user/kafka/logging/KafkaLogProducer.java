package com.lxvdnl.user.kafka.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxvdnl.user.kafka.logging.dto.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaLogProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.application.name}")
    private String serviceName;
    private static final String TOPIC = "logs";

    public void sendLog(String level, String message, String loggerClass, String requestId) {
        LogMessage logMessage = LogMessage.builder()
                .message(message)
                .level(level)
                .logger(loggerClass)
                .requestId(requestId)
                .service(serviceName)
                .timestamp(LocalDateTime.now())
                .build();

        try {
            String json = objectMapper.writeValueAsString(logMessage);
            kafkaTemplate.send(TOPIC, json);
            log.info("Sent log to Kafka: {}", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
