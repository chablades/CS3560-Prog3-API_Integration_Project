package com.example.AIWorldBuilder.integration;

import com.example.AIWorldBuilder.core.model.AppSettings;
import com.example.AIWorldBuilder.core.model.Chapter;
import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.core.model.StorySettings;
import com.example.AIWorldBuilder.core.persistence.AppDataPath;
import com.example.AIWorldBuilder.core.persistence.AppSettingsManager;
import com.example.AIWorldBuilder.core.persistence.StoryManager;
import com.example.AIWorldBuilder.editor.controller.EditorController;
import com.example.AIWorldBuilder.editor.controller.EditorControllerInterface;
import com.example.AIWorldBuilder.editor.ui.EditorViewInterface;
import com.example.AIWorldBuilder.main.controller.MainControllerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class EditorFlowTest {

    @TempDir
    Path tempDir;

    StoryManager storyManager;
    AppSettingsManager appSettingsManager;
    Story story;
    FakeView view;
    EditorController controller;

    @BeforeEach
    void setup() {
        AppDataPath.overrideBaseDir(tempDir);

        storyManager = new StoryManager();
        appSettingsManager = new AppSettingsManager();
        story = new Story("id1", "Title", "Desc");
        view = new FakeView();

        controller = new EditorController(view, story, new FakeMainController(), storyManager, appSettingsManager);
        view.setController(controller);
    }

    @Test
    void fullFlowCreateChapterEditAndSave() {
        controller.start();
        view.chapterText = "Hello world";

        controller.onSaveButtonClicked();

        String chapterId = story.getChapterIds().get(0);
        Chapter loaded = storyManager.loadChapter("id1", chapterId);

        assertEquals("Hello world", loaded.getContent());
    }

    private static class FakeView implements EditorViewInterface {
        String chapterText = "";
        boolean refreshCalled = false;

        @Override
        public void setController(EditorControllerInterface controller) {}

        @Override
        public void setStory(Story story) {}

        @Override
        public void refresh() { refreshCalled = true; }

        @Override
        public void setGenerating(boolean generating) {}

        @Override
        public void appendToken(String token) { chapterText += token; }

        @Override
        public void showError(String message) {}

        @Override
        public String getChapterText() { return chapterText; }

        @Override
        public String getPrompt() { return ""; }
    }

    private static class FakeMainController implements MainControllerInterface {
        @Override public void start() {}
        @Override public void onAppSettingsUpdated(AppSettings newSettings) {}
        @Override public void onCreateStoryRequested() {}
        @Override public void onCreateStory(String storyTitle, String description) {}
        @Override public void onSettingsButtonClicked() {}
        @Override public void onMenuButtonClicked() {}
        @Override public void onPreviousButtonClicked() {}
        @Override public void onOpenStoryEditor(Story story) {}
        @Override public void onDeleteStory(String storyId) {}
        @Override public void onStoryUpdated(Story story, StorySettings settings) {}
        @Override public void onStorySettingsButtonClicked(Story story) {}
        @Override public java.util.List<Story> getStories() { return java.util.List.of(); }
        @Override public Path getStoryPath(String storyId) { return null; }
        @Override public AppSettings getAppSettings() { return null; }
    }
}