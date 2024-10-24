package com.scribe.backend.backend.jwtSecurity.service;

import org.springframework.http.ResponseEntity;

import com.scribe.backend.backend.dto.LoginRequest;
import com.scribe.backend.backend.dto.LoginResponse;

import jakarta.servlet.http.HttpServletRequest;


public interface AuthService {

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

    ResponseEntity<LoginResponse> refresh(String refreshToken);

    public void logout(HttpServletRequest request);

}
