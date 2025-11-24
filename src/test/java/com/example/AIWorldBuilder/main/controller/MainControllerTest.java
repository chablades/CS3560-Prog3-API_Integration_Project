package com.example.AIWorldBuilder.main.controller;

import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.core.model.StorySettings;
import com.example.AIWorldBuilder.editor.ui.EditorPage;
import com.example.AIWorldBuilder.main.ui.ViewInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainControllerTest {

    private FakeView view;
    private MainController controller;

    @BeforeEach
    void setup() {
        view = new FakeView();
        controller = new MainController(view);
    }

    @Test
    void createStoryOpensEditorPage() {
        controller.onCreateStory("My Story", "Desc");

        assertEquals(Page.EDITOR, view.lastPage);
        assertNotNull(view.lastEditorPage);
    }

    @Test
    void deleteStoryRemovesMenuCard() {
        Story s = new Story("id1", "Title", "Desc");
        view.stories.add(s);

        controller.onDeleteStory("id1");

        assertFalse(view.stories.contains(s));
    }

    @Test
    void storyUpdatedUpdatesCardInView() {
        Story s = new Story("id1", "Old", "OldDesc");
        StorySettings settings = new StorySettings();
        settings.title = "New";
        settings.description = "NewDesc";

        controller.onStoryUpdated(s, settings);

        assertEquals("New", view.lastUpdatedStory.getTitle());
        assertEquals("NewDesc", view.lastUpdatedStory.getDescription());
    }

    // ---------------- fakes ----------------

    private static class FakeView implements ViewInterface {

        Page lastPage;
        EditorPage lastEditorPage;
        Story lastUpdatedStory;
        List<Story> stories = new ArrayList<>();

        @Override
        public void showPage(Page page, Object data) {
            lastPage = page;
        }

        @Override
        public void addStoryToMenu(Story story) {
            stories.add(story);
        }

        @Override
        public void removeStoryById(String storyId) {
            stories.removeIf(s -> s.getStoryId().equals(storyId));
        }

        @Override
        public void updateStoryCard(Story story) {
            lastUpdatedStory = story;
        }

        @Override
        public EditorPage getEditorPage() {
            return lastEditorPage;
        }

        @Override
        public void setEditorPage(EditorPage editorPage) {
            lastEditorPage = editorPage;
        }
    }
}