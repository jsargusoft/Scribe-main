package com.scribe.backend.backend.security.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.scribe.backend.backend.entity.Token;
import com.scribe.backend.backend.entity.User;
import com.scribe.backend.backend.security.dto.LoginRequest;
import com.scribe.backend.backend.security.dto.LoginResponse;
import com.scribe.backend.backend.security.exception.ResourceNotFoundException;
import com.scribe.backend.backend.security.jwt.JwtTokenProvider;
import com.scribe.backend.backend.security.repository.TokenRepository;
import com.scribe.backend.backend.security.repository.UserRepository;
import com.scribe.backend.backend.security.util.CookieUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AnonymousAuthenticationToken;
// import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

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
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(), loginRequest.password()
                )
        );

        String username = loginRequest.username();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        boolean accessTokenValid = tokenProvider.validateToken(accessToken);
        boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

        HttpHeaders responseHeaders = new HttpHeaders();
        Token newAccessToken, newRefreshToken;

        revokeAllTokenOfUser(user);

        if(!accessTokenValid && !refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(
                    Map.of("role", user.getRole().getAuthority()),
                    accessTokenDurationMinute,
                    ChronoUnit.MINUTES,
                    user
            );

            newRefreshToken = tokenProvider.generateRefreshToken(
                    refreshTokenDurationDay,
                    ChronoUnit.DAYS,
                    user
            );

            newAccessToken.setUser(user);
            newRefreshToken.setUser(user);

            // save tokens in db
            tokenRepository.saveAll(List.of(newAccessToken, newRefreshToken));

            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }

        if(!accessTokenValid && refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(
                    Map.of("role", user.getRole()),
                    accessTokenDurationMinute,
                    ChronoUnit.MINUTES,
                    user
            );

            addAccessTokenCookie(responseHeaders, newAccessToken);
        }

        if(accessTokenValid && refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(
                    Map.of("role", user.getRole().getAuthority()),
                    accessTokenDurationMinute,
                    ChronoUnit.MINUTES,
                    user
            );

            newRefreshToken = tokenProvider.generateRefreshToken(
                    refreshTokenDurationDay,
                    ChronoUnit.DAYS,
                    user
            );

            newAccessToken.setUser(user);
            newRefreshToken.setUser(user);

            // save tokens in db
            tokenRepository.saveAll(List.of(newAccessToken, newRefreshToken));

            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginResponse loginResponse = new LoginResponse(true, user.getRole().getName());

        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    // @Override
    // public ResponseEntity<LoginResponse> refresh(String refreshToken) {
 
    //     throw new UnsupportedOperationException("Unimplemented method 'refresh'");
    // }

    // @Override
    // public ResponseEntity<LoginResponse> logout(String accessToken, String refreshToken) {
  
    //     throw new UnsupportedOperationException("Unimplemented method 'logout'");
    // }

    // @Override
    // public UserLoggedDto getUserLoggedInfo() {

    //     throw new UnsupportedOperationException("Unimplemented method 'getUserLoggedInfo'");
    // }


    private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(token.getValue(), accessTokenDurationSecond).toString());
    }
    private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createRefreshTokenCookie(token.getValue(), refreshTokenDurationSecond).toString());
    }


    private void revokeAllTokenOfUser(User user) {
        // get all user tokens
        Set<Token> tokens = user.getTokens();

        tokens.forEach(token -> {
            if(token.getExpiryDate().isBefore(LocalDateTime.now()))
                tokenRepository.delete(token);
            else if(!token.isDisabled()) {
                token.setDisabled(true);
                tokenRepository.save(token);
            }
        });
    }

}
