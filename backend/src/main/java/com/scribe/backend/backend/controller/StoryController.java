package com.scribe.backend.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scribe.backend.backend.dto.CreateStory;
import com.scribe.backend.backend.dto.FullStoryDetail;
import com.scribe.backend.backend.entity.Stories;
import com.scribe.backend.backend.service.StoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/story")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @PostMapping("/create")
    public String createStory(@Valid @RequestBody CreateStory story){
        return storyService.createStory(story);
    }

    @GetMapping("/getAll")
    public List<FullStoryDetail> getAllStories(){
        return storyService.getAllStories();
    }
    
}
