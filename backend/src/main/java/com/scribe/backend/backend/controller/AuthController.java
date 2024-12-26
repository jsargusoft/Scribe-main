package com.scribe.backend.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scribe.backend.backend.dto.LoginRequest;
import com.scribe.backend.backend.dto.LoginResponse;
import com.scribe.backend.backend.jwtSecurity.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(
            @RequestHeader(value = "refresh_token", required = true) String refreshToken) {
        return authService.refresh(refreshToken);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        authService.logout(request);
    }
}
