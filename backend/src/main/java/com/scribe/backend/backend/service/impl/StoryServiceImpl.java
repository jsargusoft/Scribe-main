package com.scribe.backend.backend.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.scribe.backend.backend.dto.CreateStory;
import com.scribe.backend.backend.dto.FullStoryDetail;
import com.scribe.backend.backend.entity.Genre;
import com.scribe.backend.backend.entity.Stories;
import com.scribe.backend.backend.entity.User;
import com.scribe.backend.backend.repository.GenreRepository;
import com.scribe.backend.backend.repository.StoryRepository;
import com.scribe.backend.backend.repository.UserRepository;
import com.scribe.backend.backend.service.StoryService;
import com.scribe.backend.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final UserService userService;
    private final StoryRepository storyRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;


    @Override
    public String createStory(CreateStory createStory) {

        User user = userService.getUserById(Integer.parseInt(createStory.getAuthorId()));
        if (!user.is_author()) {
            throw new RuntimeException("User is not an author.");
        }

        Set<Genre> genres = new HashSet<>();
        for (Genre genre : createStory.getGenres()) {
            Genre fetchedGenre = genreRepository.findById(genre.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found with ID: " + genre.getId()));
            genres.add(fetchedGenre);
        }

        Stories story = Stories.builder()
                .authorId(createStory.getAuthorId())
                .title(createStory.getTitle())
                .description(createStory.getDescription())
                .genres(genres)
                .image(createStory.getImage())
                .created_at(LocalDateTime.now())
                .last_updated(LocalDateTime.now())
                .build();

        System.out.println(story);
        storyRepository.save(story);

        return "Story created successfully.";
    }

    @Override
    public List<FullStoryDetail> getAllStories() {
        List<Stories> stories = storyRepository.findAll();

        List<FullStoryDetail> storyDetails = new ArrayList<>();
        for (Stories story : stories) {
            User author = userRepository.findById(Integer.parseInt(story.getAuthorId()))
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            System.out.println("author"+author);
            FullStoryDetail detail = new FullStoryDetail();
            detail.setTitle(story.getTitle());
            detail.setImage(story.getImage());
            detail.setAuthor_name(author.getFirstName() + " " + author.getLastName());
            detail.setIsKidsAppropriate(story.getKids_appropriate());
            detail.setGenres(story.getGenres());
            detail.setDecription(story.getDescription());
            storyDetails.add(detail);
            System.out.println(detail.getAuthor_name());
        }
        return storyDetails;

    }

}