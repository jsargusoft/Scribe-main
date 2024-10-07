package com.scribe.backend.backend.security.service;

import org.springframework.http.ResponseEntity;


import com.scribe.backend.backend.security.dto.LoginRequest;
import com.scribe.backend.backend.security.dto.LoginResponse;


public interface AuthService {

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken);

    ResponseEntity<LoginResponse> refresh(String refreshToken);

    ResponseEntity<LoginResponse> logout(String accessToken, String refreshToken);

    // UserLoggedDto getUserLoggedInfo();
}
