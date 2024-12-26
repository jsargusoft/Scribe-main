package com.scribe.backend.backend.dto;

import java.util.Set;

import com.scribe.backend.backend.entity.Genre;

import lombok.Data;

@Data
public class FullStoryDetail {
    
    private String title;

    private String image;

    private String author_name;

    private Boolean isKidsAppropriate;

    private Set<Genre> genres;

    private String decription;
}
