package com.scribe.backend.backend.jwtSecurity.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.scribe.backend.backend.dto.LoginRequest;
import com.scribe.backend.backend.dto.LoginResponse;
import com.scribe.backend.backend.entity.Token;
import com.scribe.backend.backend.entity.User;
import com.scribe.backend.backend.enums.TokenType;
import com.scribe.backend.backend.exception.AppException;
import com.scribe.backend.backend.exception.ResourceNotFoundException;
import com.scribe.backend.backend.jwtSecurity.jwt.JwtTokenProvider;
import com.scribe.backend.backend.jwtSecurity.service.AuthService;
import com.scribe.backend.backend.jwtSecurity.util.CookieUtil;
import com.scribe.backend.backend.repository.TokenRepository;
import com.scribe.backend.backend.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${JWT_ACCESS_TOKEN_DURATION_MINUTE}")
    private long accessTokenDurationMinute;
    @Value("${JWT_ACCESS_TOKEN_DURATION_SECOND}")
    private long accessTokenDurationSecond;
    @Value("${JWT_REFRESH_TOKEN_DURATION_DAY}")
    private long refreshTokenDurationDay;
    @Value("${JWT_REFRESH_TOKEN_DURATION_SECOND}")
    private long refreshTokenDurationSecond;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider tokenProvider;
    private final CookieUtil cookieUtil;
    private final AuthenticationManager authenticationManager;

@Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            if (userDetails.getUsername().equals(loginRequest.email())) {
                throw new RuntimeException("User is currently logged in.");
            }
        }

        Authentication currAuth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.password()));
        String email = loginRequest.email();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));
        HttpHeaders responseHeaders = new HttpHeaders(); 
        Token newAccessToken = null;
        Token newRefreshToken = null;

        newAccessToken = tokenProvider.generateAccessToken(
                Map.of("role", user.getRole()),
                accessTokenDurationMinute,
                ChronoUnit.MINUTES,
                user);

        newRefreshToken = tokenProvider.generateRefreshToken(
                refreshTokenDurationDay,
                ChronoUnit.DAYS,
                user);

        newAccessToken.setUser(user);
        newRefreshToken.setUser(user);

        tokenRepository.saveAll(List.of(newAccessToken, newRefreshToken));

        addAccessTokenCookie(responseHeaders, newAccessToken);
        addRefreshTokenCookie(responseHeaders, newRefreshToken);

        SecurityContextHolder.getContext().setAuthentication(currAuth);

        LoginResponse loginResponse = new LoginResponse(true,user.getRole().getName());

        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);

    }

    @Override
    public ResponseEntity<LoginResponse> refresh(String refreshToken) {

        boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

        if (!refreshTokenValid)
            throw new AppException(HttpStatus.BAD_REQUEST, "Refresh token is invalid");

        String username = tokenProvider.getUsernameFromToken(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));
        tokenRepository.deleteAccessTokenByUsername(username, TokenType.ACCESS);

        Token newAccessToken = tokenProvider.generateAccessToken(
                Map.of("role", user.getRole().getAuthority()),
                accessTokenDurationMinute,
                ChronoUnit.MINUTES,
                user);

        HttpHeaders responseHeaders = new HttpHeaders();
        addAccessTokenCookie(responseHeaders, newAccessToken);

        LoginResponse loginResponse = new LoginResponse(true, user.getRole().getName());

        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    @Override
    public void logout(HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No user is currently logged in.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);

        if (user != null) {
            tokenRepository.deleteAllTokensByUserId(user.getUser_id());
        }

        SecurityContextHolder.clearContext();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.deleteAccessTokenCookie().toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.deleteRefreshTokenCookie().toString());

    }

    private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE,
                cookieUtil.createAccessTokenCookie(token.getValue(), accessTokenDurationSecond).toString());
    }

    private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE,
                cookieUtil.createRefreshTokenCookie(token.getValue(), refreshTokenDurationSecond).toString());
    }

}
