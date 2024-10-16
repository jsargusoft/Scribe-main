package com.scribe.backend.backend.security.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.scribe.backend.backend.entity.Token;
import com.scribe.backend.backend.entity.User;
import com.scribe.backend.backend.enums.TokenType;
import com.scribe.backend.backend.repository.TokenRepository;
import com.scribe.backend.backend.repository.UserRepository;
import com.scribe.backend.backend.security.dto.LoginRequest;
import com.scribe.backend.backend.security.dto.LoginResponse;
import com.scribe.backend.backend.security.exception.AppException;
import com.scribe.backend.backend.security.exception.ResourceNotFoundException;
import com.scribe.backend.backend.security.jwt.JwtTokenProvider;
import com.scribe.backend.backend.security.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import org.springframework.security.core.userdetails.UserDetails;

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
        
        String email = loginRequest.email();

        System.out.println(email);

        String password = loginRequest.password();
        
        System.out.println(password);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(), loginRequest.password()));

        // String email = loginRequest.email();

        System.out.println(email);

        User user = userRepository.findByEmail(email).orElseThrow(
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
            // tokenRepository.deleteAllUserTokens(refreshToken,accessToken);
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

        // Authentication authentication = authenticationManager.authenticate(
        //     new UsernamePasswordAuthenticationToken(
        //             user.getUsername(), loginRequest.password()
        //     )
        // );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String name = user.getFirstName()+ " " + user.getLastName();

        LoginResponse loginResponse = new LoginResponse(true, user.getRole().getName(),name);

        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);


    }

    @Override
    public ResponseEntity<LoginResponse> refresh(String refreshToken) {
 
        boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

        if(!refreshTokenValid)
            throw new AppException(HttpStatus.BAD_REQUEST, "Refresh token is invalid");

        String username = tokenProvider.getUsernameFromToken(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        tokenRepository.deleteAccessTokenByUsername(username, TokenType.ACCESS); 

        Token newAccessToken = tokenProvider.generateAccessToken(
                Map.of("role", user.getRole().getAuthority()),
                accessTokenDurationMinute,
                ChronoUnit.MINUTES,
                user
        );

        HttpHeaders responseHeaders = new HttpHeaders();
        addAccessTokenCookie(responseHeaders, newAccessToken);

        LoginResponse loginResponse = new LoginResponse(true,user.getRole().getName(),user.getFirstName());
        System.out.println("new access token:" +newAccessToken + "refresh token" + refreshToken);

        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    @Override
    public ResponseEntity<LoginResponse> logout(HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No user is currently logged in.");
        }
    
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
    
        // If user is found, delete all tokens for that user
        if (user != null) {
            tokenRepository.deleteAllTokensByUserId(user.getUser_id());
            System.out.println("All tokens deleted for user: " + user.getUsername());
        }
    
        // Clear security context
        SecurityContextHolder.clearContext();
    
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.deleteAccessTokenCookie().toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.deleteRefreshTokenCookie().toString());
    
        LoginResponse loginResponse = new LoginResponse(false, null,user.getFirstName());
        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);

    }

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
