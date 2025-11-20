package com.example.AIWorldBuilder.controller;

import com.example.AIWorldBuilder.view.ViewInterface;
import com.example.AIWorldBuilder.persistence.*;
import com.example.AIWorldBuilder.model.Story;
import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

public class MainController implements ControllerInterface {

    private ViewInterface view;
    private APIKeyManager apiKeyManager;
    private StoryManager storyManager;
    private Stack<Page> navStack = new Stack<>();
    
    public MainController(ViewInterface view) {
        this.view = view;
        this.apiKeyManager = new APIKeyManager();    
        this.storyManager = new StoryManager();
    }

    @Override
    public void start() {
        if (!apiKeyManager.hasKey()) {
            System.out.println("No API Key found, showing API Key page.");
            showPage(Page.API_KEY);
        } else {
            System.out.println("API Key found, showing Menu page.");
            showPage(Page.MENU);
        }
    }

    @Override
    public void onApiKeyUpdated(String newApiKey) {
        apiKeyManager.saveKey(newApiKey);
    }

    @Override public void onCreateStoryRequested() {
        showPage(Page.CREATE_STORY);
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
        showPage(Page.SETTINGS);
    }

    // Function for menu button click
    @Override
    public void onMenuButtonClicked() {
        showPage(Page.MENU);
    }

    // Function for previous button click
    @Override
    public void onPreviousButtonClicked() {
        if (navStack.size() > 1) {
            // Remove current page
            navStack.pop();

            // Go back to the previous page
            Page previousPage = navStack.peek();
            view.showPage(previousPage, null);
        }
    }

    // Function to delete a story
    @Override
    public void onDeleteStory(String storyId) {
        boolean success = storyManager.deleteStory(storyId);
        if (success) {
            System.out.println("Deleted story with ID: " + storyId);
            showPage(Page.MENU);
        } else {
            System.out.println("Failed to delete story with ID: " + storyId);
        }
    }

    // Function to update a story
    @Override
    public void onStoryUpdated(Story story) {
        storyManager.saveStory(story);
        System.out.println("Updated story with ID: " + story.getStoryId());
    }

    // Function for story settings button click
    @Override
    public void onStorySettingsButtonClicked(Story story) {
        showPage(Page.STORY_SETTINGS, story);
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

    // Helper to show page and update current/previous page

    private void showPage(Page page) {
        showPage(page, null);
    }

    private void showPage(Page page, Object data) {

        if (navStack.isEmpty() || navStack.peek() != page) {
            navStack.push(page);
        }

        view.showPage(page, data);
    }
}
