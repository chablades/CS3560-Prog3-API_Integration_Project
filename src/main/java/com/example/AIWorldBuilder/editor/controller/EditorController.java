package com.example.AIWorldBuilder.editor.controller;

import com.example.AIWorldBuilder.editor.controller.*;
import com.example.AIWorldBuilder.main.controller.*;
import com.example.AIWorldBuilder.editor.ui.EditorPage;
import com.example.AIWorldBuilder.editor.ui.EditorViewInterface;
import com.example.AIWorldBuilder.core.model.*;

import javax.swing.SwingUtilities;

import com.example.AIWorldBuilder.core.ai.*;
import com.example.AIWorldBuilder.core.service.AIService;
import com.example.AIWorldBuilder.core.persistence.StoryManager;
import com.example.AIWorldBuilder.core.persistence.AppSettingsManager;

public class EditorController implements EditorControllerInterface, AIStreamListener {

    private EditorViewInterface editorPage;
    private Story story;
    private Chapter currentChapter;
    private MainControllerInterface mainController;
    private StoryManager storyManager;
    private AppSettingsManager appSettingsManager;
    private AIService aiService;

    private boolean dirty = false;

    public EditorController(EditorViewInterface editorPage,
            Story story,
            MainControllerInterface mainController,
            StoryManager storyManager,
            AppSettingsManager appSettingsManager) {
        
        this.editorPage = editorPage;
        this.story = story;
        this.currentChapter = null;
        this.mainController = mainController;
        this.storyManager = storyManager;
        this.appSettingsManager = appSettingsManager;
        this.aiService = new AIService();
    }

    @Override
    public void start() {
        editorPage.setStory(story);

        // Ensure chapters list exists
        if (story.getChapterIds().isEmpty()) {
            Chapter first = new Chapter(
                "chapter1",
                "Chapter 1",
                ""
            );

            storyManager.addChapter(story, first);
            storyManager.saveStory(story);
        }

        // Load first chapter
        Chapter chapter = storyManager.loadChapter(
            story.getStoryId(),
            story.getChapterIds().get(0)
        );
        this.currentChapter = chapter;
        editorPage.refresh();
    }

    // Handle content generation request
    @Override
    public void onGenerateContentRequest(String prompt) {
        // Create context for AI request (only last 5000 characters or less of current chapter)
        String context = "";
        if (currentChapter != null && currentChapter.getContent() != null) {
            String content = currentChapter.getContent();
            if (content.length() > 5000) {
                context = content.substring(content.length() - 5000);
            } else {
                context = content;
            }
        }
        AIRequest request = new AIRequest(
            AIProvider.GOOGLE,
            AIRequestType.TEXT,
            appSettingsManager.getApiKey(),
            prompt,
            context,
            true
        );
        editorPage.setGenerating(true);
        aiService.send(request, this);
    }

    // Handle cancellation of content generation
    @Override
    public void onCancelGenerateContentRequest() {
        aiService.cancel();
        editorPage.setGenerating(false);
    }

    // Handle exit button click
    @Override
    public void onExitButtonClicked() {
        mainController.onMenuButtonClicked();
    }
    // Handle save button click
    @Override
    public void onSaveButtonClicked() {
        storyManager.saveStory(story);
        String chapterText = editorPage.getChapterText();
        currentChapter.setContent(chapterText);
        storyManager.saveChapter(story.getStoryId(), currentChapter);
        dirty = false;
        System.out.println("Story and chapter saved.");
    }
    // Handle settings button click
    @Override
    public void onSettingsButtonClicked() {
        mainController.onSettingsButtonClicked();
    }

    // AISTREAMLISTENER METHODS
    // Handle token received from AI stream
    @Override
    public void onToken(String token) {
        SwingUtilities.invokeLater(() -> {
            editorPage.appendToken(token);
        });
    }
    // Handle completion of AI stream
    @Override
    public void onComplete() {
        SwingUtilities.invokeLater(() -> {
            editorPage.setGenerating(false);
            System.out.println("AI Stream Complete");
        });
    }
    // Handle error in AI stream
    @Override
    public void onError(Exception e) {
        System.out.println("AI Stream Error: " + e.getMessage());
        SwingUtilities.invokeLater(() -> {
            if (e instanceof InvalidApiKeyException) {
                editorPage.showError("Missing/Invalid API Key. Please update your key in Settings.");
            } else {
                editorPage.showError(e.getMessage());
            }
            editorPage.setGenerating(false);
        });
    }
    // Handle cancellation of AI stream
    @Override
    public void onCancelled() {
        SwingUtilities.invokeLater(() -> {
            editorPage.setGenerating(false);
            System.out.println("AI Stream Cancelled");
        });
    }

    // Detect if content is changed
    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    // Get chapter by ID
    @Override
    public Chapter getChapterById(Story story, String chapterId) {
        for (String id : story.getChapterIds()) {
            if (id.equals(chapterId)) {
                return storyManager.loadChapter(story.getStoryId(), chapterId);
            }
        }
        return null;
    }
    // Get chapters of the story
    @Override
    public java.util.List<Chapter> getChapters(Story story) {
        java.util.List<Chapter> chapters = new java.util.ArrayList<>();
        for (String chapterId : story.getChapterIds()) {
            Chapter chapter = storyManager.loadChapter(story.getStoryId(), chapterId);
            if (chapter != null) {
                chapters.add(chapter);
            }
        }
        return chapters;
    }

    // Get the current story
    @Override
    public Story getStory() {
        return story;
    }

    // Set the current story
    @Override
    public void setStory(Story story) {
        this.story = story;
    }

    // Get the current chapter
    public Chapter getCurrentChapter() {
        return this.currentChapter;
    }
        // Set the current chapter
    public void setCurrentChapter(Chapter chapter) {
        this.currentChapter = chapter;
    }
}