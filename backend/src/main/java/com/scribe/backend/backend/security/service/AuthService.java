package com.scribe.backend.backend.security.service;

import org.springframework.http.ResponseEntity;


import com.scribe.backend.backend.security.dto.LoginRequest;
import com.scribe.backend.backend.security.dto.LoginResponse;

import jakarta.servlet.http.HttpServletRequest;


public interface AuthService {

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken);

    ResponseEntity<LoginResponse> refresh(String refreshToken);

    public void logout(HttpServletRequest request);

}
