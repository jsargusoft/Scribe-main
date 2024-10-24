package com.scribe.backend.backend.jwtSecurity.jwt;


import org.springframework.security.core.userdetails.UserDetails;

import com.scribe.backend.backend.entity.Token;

import java.time.LocalDateTime;
// import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Map;

public interface JwtTokenProvider {
    Token generateAccessToken(Map<String, Object> extraClaims, long duration, TemporalUnit durationType, UserDetails user);
    Token generateRefreshToken(long duration,  TemporalUnit durationType, UserDetails user);
    boolean validateToken(String tokenValue);
    String getUsernameFromToken(String tokenValue);
    LocalDateTime getExpiryDateFromToken(String tokenValue);
}
