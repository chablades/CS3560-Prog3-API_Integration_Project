package com.example.AIWorldBuilder.editor.controller;

import com.example.AIWorldBuilder.editor.controller.*;
import com.example.AIWorldBuilder.main.controller.*;
import com.example.AIWorldBuilder.editor.ui.EditorPage;
import com.example.AIWorldBuilder.editor.ui.EditorViewInterface;
import com.example.AIWorldBuilder.core.model.*;

import javax.swing.SwingUtilities;

import org.checkerframework.checker.units.qual.m;

import java.util.List;
import java.util.ArrayList;

import com.example.AIWorldBuilder.core.ai.*;
import com.example.AIWorldBuilder.core.ai.strategy.*;
import com.example.AIWorldBuilder.core.ai.factory.*;
import com.example.AIWorldBuilder.core.service.AIService;
import com.example.AIWorldBuilder.core.persistence.StoryManager;
import com.example.AIWorldBuilder.core.persistence.AppSettingsManager;

public class EditorController implements EditorControllerInterface, AIStreamListener {

    private EditorViewInterface editorPage;
    private Story story;
    private Chapter currentChapter;
    private List<Chapter> chapters;
    private MainControllerInterface mainController;
    private StoryManager storyManager;
    private AppSettingsManager appSettingsManager;
    private AIService aiService;
    private PromptMode currentPromptMode = PromptMode.CONTINUE;

    private boolean dirty = false;

    public EditorController(EditorViewInterface editorPage,
            Story story,
            MainControllerInterface mainController,
            StoryManager storyManager,
            AppSettingsManager appSettingsManager) {
        
        this.editorPage = editorPage;
        this.story = story;
        this.currentChapter = null;
        this.chapters = null;
        this.mainController = mainController;
        this.storyManager = storyManager;
        this.appSettingsManager = appSettingsManager;
        this.aiService = new AIService();
    }

    @Override
    public void start() {
        this.chapters = cloneChapters(getChaptersFromPersistence());
        this.currentChapter = null;
        this.dirty = false;

        editorPage.setStory(story);
        editorPage.refresh();
    }

    private List<Chapter> cloneChapters(List<Chapter> source) {
        List<Chapter> copy = new ArrayList<>();
        for (Chapter c : source) {
            copy.add(new Chapter(
                c.getChapterId(),
                c.getTitle(),
                c.getContent()
            ));
        }
        return copy;
    }

    // Handle content generation request
    @Override
    public void onGenerateContentRequest(String userPrompt) {

        getChapterDataFromView();

        PromptStrategy strategy =
            PromptStrategyFactory.create(currentPromptMode);

        String finalPrompt = strategy.buildPrompt(
            userPrompt,
            currentChapter.getContent()
        );

        System.out.println(finalPrompt);

        // empty chat text area if prompt mode is REWRITE
        if (currentPromptMode == PromptMode.REWRITE) {
            editorPage.setChatText("");
        }

        AIRequest request = new AIRequest(
            AIProvider.GOOGLE,
            AIRequestType.TEXT,
            appSettingsManager.getApiKey(),
            finalPrompt,
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
        mainController.onCloseStoryEditor();
    }
    // Handle save button click
    @Override
    public void onSaveButtonClicked() {
        getChapterDataFromView();

        // rebuild story chapter IDs from working copy
        story.getChapterIds().clear();

        for (Chapter chapter : chapters) {
            story.addChapterId(chapter.getChapterId());
            storyManager.saveChapter(story.getStoryId(), chapter);
        }

        storyManager.saveStory(story);
        dirty = false;

        // Show message to user
        editorPage.showMessage("Story saved successfully.");
    }

    // Handle chapter save button click
    @Override
    public void onChapterSaveButtonClicked() {
        // update the currentChapter from chapters list
        for (int i = 0; i < chapters.size(); i++) {
            if (chapters.get(i).getChapterId().equals(currentChapter.getChapterId())) {
                getChapterDataFromView();
                chapters.set(i, currentChapter);
                break;
            }
        }
    }
    // Handle chapter selection
    @Override
    public void onChapterSelect(String chapterId) {
        for (Chapter chapter : chapters) {
            if (chapter.getChapterId().equals(chapterId)) {
                this.currentChapter = chapter;
                editorPage.openChapter(chapterId);
                break;
            }
        }
    }

    // Handle chapter delete button click
    @Override
    public void onChapterDeleteButtonClicked(String chapterId) {
        deleteChapterById(chapterId);
        editorPage.refresh();
    }

    private void getChapterDataFromView() {
        if (currentChapter != null) {
            String newTitle = editorPage.getChapterTitle();
            String newContent = editorPage.getChapterText();
            currentChapter.setTitle(newTitle);
            currentChapter.setContent(newContent);
        }
    }
    // Handle chapter exit button click
    @Override
    public void onChapterExitButtonClicked() {
        this.currentChapter = null;
        editorPage.exitChapter();
        editorPage.refresh();
    }

    // handle chapter title change
    @Override
    public void onChapterTitleChanged(String newTitle) {
        if (currentChapter != null) {
            currentChapter.setTitle(newTitle);
            dirty = true;
        }
    }
    // Handle create chapter button click
    @Override
    public void onCreateChapterButtonClicked() {
        createNewChapter("New Chapter " + (chapters.size() + 1));
        editorPage.refresh();
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
    // Get chapters of the story from persistence
    @Override
    public List<Chapter> getChaptersFromPersistence() {
        List<Chapter> chapters = new ArrayList<>();
        for (String chapterId : this.story.getChapterIds()) {
            Chapter chapter = storyManager.loadChapter(this.story.getStoryId(), chapterId);
            if (chapter != null) {
                chapters.add(chapter);
            }
        }
        return chapters;
    }

    // Get chapters of the story
    @Override
    public List<Chapter> getChapters() {
        if (this.chapters == null) {
            this.chapters = getChaptersFromPersistence();
        }
        return this.chapters;
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

    // Set the current prompt strategy mode
    @Override
    public void setStrategyMode(PromptMode mode) {
        this.currentPromptMode = mode;
    }

    // Get the current chapter
    public Chapter getCurrentChapter() {
        return this.currentChapter;
    }
    // Set the current chapter
    public void setCurrentChapter(Chapter chapter) {
        this.currentChapter = chapter;
    }

    // Delete chapter by ID
    @Override
    public void deleteChapterById(String chapterId) {
        chapters.removeIf(c -> c.getChapterId().equals(chapterId));

        if (currentChapter != null &&
            currentChapter.getChapterId().equals(chapterId)) {
            currentChapter = null;
            editorPage.exitChapter();
        }

        dirty = true;
    }
    // Create a new chapter
    @Override
    public Chapter createNewChapter(String title) {
        String newChapterId = "chapter" + (chapters.size() + 1);
        Chapter newChapter = new Chapter(newChapterId, title, "");

        chapters.add(newChapter);
        dirty = true;

        return newChapter;
    }
}