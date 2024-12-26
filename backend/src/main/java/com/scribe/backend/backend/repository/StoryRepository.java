package com.scribe.backend.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scribe.backend.backend.entity.Stories;

public interface StoryRepository extends JpaRepository<Stories, Integer> {

    List<Stories> findByAuthorId(String authorId);

}