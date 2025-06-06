package com.lxvdnl.logging.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "logs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    @Id
    private UUID id;

    private String level; // info, warn, error etc

    private String service; // service from

    private String logger; // class from

    @Column(length = 2048)
    private String message;

    private String requestId;

    private LocalDateTime timestamp;

}
