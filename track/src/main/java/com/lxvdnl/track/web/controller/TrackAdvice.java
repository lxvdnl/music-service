package com.lxvdnl.track.web.controller;

import com.lxvdnl.track.exception.TrackNotFoundException;
import com.lxvdnl.track.exception.UploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TrackAdvice {

    @ExceptionHandler(UploadException.class)
    public ResponseEntity<String> handleUploadException(UploadException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TrackNotFoundException.class)
    public ResponseEntity<String> handleTrackNotFoundException(TrackNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
