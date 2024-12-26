package com.scribe.backend.backend.service;

import java.util.List;

import com.scribe.backend.backend.dto.CreateStory;
import com.scribe.backend.backend.dto.FullStoryDetail;

public interface StoryService {

    public String createStory(CreateStory story);

    public List<FullStoryDetail> getAllStories();
    
}
