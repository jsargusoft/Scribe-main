package com.scribe.backend.backend.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scribe.backend.backend.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{


}
