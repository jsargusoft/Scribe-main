package com.scribe.backend.backend.dto;

import java.util.Set;

import com.scribe.backend.backend.entity.Genre;
import com.scribe.backend.backend.entity.User;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class CreateStory {

    @NotBlank(message = "User Id is required.")
    private String authorId;

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotEmpty(message = "Provide at least 1 genre.")
    private Set<Genre> genres;

    private String image;
}
