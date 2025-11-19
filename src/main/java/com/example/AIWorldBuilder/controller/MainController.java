package com.example.AIWorldBuilder.controller;

import com.example.AIWorldBuilder.view.ViewInterface;
import com.example.AIWorldBuilder.persistence.*;
import com.example.AIWorldBuilder.model.Story;
import java.nio.file.Path;
import java.util.List;

public class MainController implements ControllerInterface {

    private ViewInterface view;
    private APIKeyManager apiKeyManager;
    private StoryManager storyManager;
    
    public MainController(ViewInterface view) {
        this.view = view;
        this.apiKeyManager = new APIKeyManager();    
        this.storyManager = new StoryManager();
    }

    @Override
    public void start() {
        if (!apiKeyManager.hasKey()) {
            System.out.println("No API Key found, showing API Key page.");
            view.showApiKeyPage();
        } else {
            System.out.println("API Key found, showing Menu page.");
            view.showMenuPage();
        }
    }

    @Override
    public void onApiKeyUpdated(String newApiKey) {
        apiKeyManager.saveKey(newApiKey);
    }

    @Override public void onCreateStoryRequested() {
        view.showCreateStoryPage();
    }
    
    @Override
    public void onCreateStory(String storyTitle, String description) {
        String storyId = "story_" + System.currentTimeMillis();
        Path storyDir = storyManager.createStoryFolder(storyId);

        if (storyDir != null) {
            System.out.println("Created new story folder at: " + storyDir.toString());
        } else {
            System.out.println("Failed to create new story folder.");
            return;
        }

        Story story = new Story(storyId, storyTitle, description);
        storyManager.saveStory(story);

    }

    // Function for settings button click
    @Override
    public void onSettingsButtonClicked() {
        view.showSettingsPage();
    }

    // Function for menu button click
    @Override
    public void onMenuButtonClicked() {
        view.showMenuPage();
    }

    // Function to get stories
    @Override
    public List<Story> getStories() {
        return storyManager.loadAllStories();
    }

    // Function to get story path
    @Override
    public Path getStoryPath(String storyId) {
        return storyManager.getStoryPath(storyId);
    }

    // Function to get API Key
    @Override
    public String getApiKey() {
        return apiKeyManager.loadKey();
    }
}
