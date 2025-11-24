package com.example.AIWorldBuilder.editor.controller;

import com.example.AIWorldBuilder.core.model.AppSettings;
import com.example.AIWorldBuilder.core.model.Chapter;
import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.core.model.StorySettings;
import com.example.AIWorldBuilder.core.persistence.AppSettingsManager;
import com.example.AIWorldBuilder.core.persistence.StoryManager;
import com.example.AIWorldBuilder.editor.ui.EditorViewInterface;
import com.example.AIWorldBuilder.main.controller.MainControllerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EditorControllerTest {

    private FakeView view;
    private FakeStoryManager storyManager;
    private FakeAppSettingsManager appSettingsManager;
    private FakeMainController mainController;
    private EditorController controller;
    private Story story;

    @BeforeEach
    void setup() {
        story = new Story("id1", "Title", "Desc");
        view = new FakeView();
        storyManager = new FakeStoryManager();
        appSettingsManager = new FakeAppSettingsManager();
        mainController = new FakeMainController();

        controller = new EditorController(view, story, mainController, storyManager, appSettingsManager);
        view.setController(controller);
    }

    @Test
    void startCreatesFirstChapterIfNoneExistAndRefreshesView() {
        assertTrue(story.getChapterIds().isEmpty());

        controller.start();

        assertFalse(story.getChapterIds().isEmpty());
        assertNotNull(controller.getCurrentChapter());
        assertTrue(view.refreshCalled);
    }

    @Test
    void saveButtonSavesChapterContent() {
        controller.start();
        view.chapterText = "Hello content";

        controller.onSaveButtonClicked();

        assertNotNull(storyManager.lastSavedChapter);
        assertEquals("Hello content", storyManager.lastSavedChapter.getContent());
    }

    // ---------------- fakes ----------------

    private static class FakeView implements com.example.AIWorldBuilder.editor.ui.EditorViewInterface {

        boolean refreshCalled = false;
        String chapterText = "";

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

    private static class FakeStoryManager extends StoryManager {
        Chapter lastSavedChapter;

        @Override
        public void saveChapter(String storyId, Chapter chapter) {
            lastSavedChapter = chapter;
        }
    }

    private static class FakeAppSettingsManager extends AppSettingsManager {
        @Override
        public String getApiKey() { return "fake"; }
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
        @Override public java.nio.file.Path getStoryPath(String storyId) { return null; }
        @Override public AppSettings getAppSettings() { return null; }
    }
}