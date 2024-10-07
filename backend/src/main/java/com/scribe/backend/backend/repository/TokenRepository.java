package com.scribe.backend.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scribe.backend.backend.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{

    // Optional<Token> deleteAllUserTokens(String accesToken,String refreshToken);

    Optional<Token> findByValue(String token);

}
