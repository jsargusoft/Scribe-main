package com.scribe.backend.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scribe.backend.backend.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre,Integer>{
    
}
