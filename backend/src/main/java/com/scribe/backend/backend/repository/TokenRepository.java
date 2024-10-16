package com.scribe.backend.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.scribe.backend.backend.entity.Token;
import com.scribe.backend.backend.enums.TokenType;

public interface TokenRepository extends JpaRepository<Token, Integer>{

    // Optional<Token> deleteAllUserTokens(String accesToken,String refreshToken);

    Optional<Token> findByValue(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.user.username = ?1 AND t.type = ?2")
    void deleteAccessTokenByUsername(String username, TokenType type);

    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.user.id = ?1")
    void deleteAllTokensByUserId(Integer userId);

}
