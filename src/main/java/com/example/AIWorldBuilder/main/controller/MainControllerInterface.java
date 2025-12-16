package com.example.AIWorldBuilder.main.controller;

import java.util.List;

import com.example.AIWorldBuilder.core.model.*;

import java.nio.file.Path;

public interface MainControllerInterface {
    void start();
    void onAppSettingsUpdated(AppSettings newSettings);
    void onCreateStoryRequested();
    void onCreateStory(String storyTitle, String description);
    void onSettingsButtonClicked();
    void onMenuButtonClicked();
    void onPreviousButtonClicked();
    void onOpenStoryEditor(Story story);
    void onCloseStoryEditor();
    void onDeleteStory(String storyId);
    void onStoryUpdated(Story story, StorySettings settings);
    void onStorySettingsButtonClicked(Story story);
    List<Story> getStories();
    Path getStoryPath(String storyId);
    AppSettings getAppSettings();
}
