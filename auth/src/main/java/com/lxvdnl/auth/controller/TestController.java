package com.lxvdnl.auth.controller;

import com.lxvdnl.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("working");
    }

}
