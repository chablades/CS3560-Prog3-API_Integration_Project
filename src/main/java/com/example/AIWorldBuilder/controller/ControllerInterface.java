package com.example.AIWorldBuilder.controller;

import java.util.List;
import com.example.AIWorldBuilder.model.Story;
import java.nio.file.Path;

public interface ControllerInterface {
    void start();
    void onApiKeyUpdated(String newApiKey);
    void onCreateStoryRequested();
    void onCreateStory(String storyTitle, String description);
    void onSettingsButtonClicked();
    void onMenuButtonClicked();
    void onPreviousButtonClicked();
    void onDeleteStory(String storyId);
    void onStoryUpdated(Story story);
    void onStorySettingsButtonClicked(Story story);
    List<Story> getStories();
    Path getStoryPath(String storyId);
    String getApiKey();
}
