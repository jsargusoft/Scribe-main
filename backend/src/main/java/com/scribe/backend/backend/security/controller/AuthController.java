package com.scribe.backend.backend.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scribe.backend.backend.security.dto.LoginRequest;
import com.scribe.backend.backend.security.dto.LoginResponse;
import com.scribe.backend.backend.security.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @CookieValue(name = "access_token", required = false) String accessToken,
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest, accessToken, refreshToken);
    }
}
