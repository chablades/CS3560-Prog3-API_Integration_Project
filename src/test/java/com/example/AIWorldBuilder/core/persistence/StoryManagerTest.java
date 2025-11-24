package com.example.AIWorldBuilder.core.persistence;

import com.example.AIWorldBuilder.core.model.Chapter;
import com.example.AIWorldBuilder.core.model.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoryManagerTest {

    @TempDir
    Path tempDir;

    StoryManager manager;

    @BeforeEach
    void setup() {
        AppDataPath.overrideBaseDir(tempDir);
        manager = new StoryManager();
    }

    @Test
    void saveAndLoadStoryWorks() {
        Story s = new Story("id1", "Title", "Desc");
        manager.saveStory(s);

        Story loaded = manager.loadStory("id1");

        assertNotNull(loaded);
        assertEquals("Title", loaded.getTitle());
        assertEquals("Desc", loaded.getDescription());
    }

    @Test
    void saveAndLoadChapterWorks() {
        Story s = new Story("id1", "Title", "Desc");
        manager.saveStory(s);

        Chapter c = new Chapter("c1", "Chapter 1", "Hello");
        manager.saveChapter("id1", c);

        Chapter loaded = manager.loadChapter("id1", "c1");

        assertNotNull(loaded);
        assertEquals("Hello", loaded.getContent());
    }

    @Test
    void deleteStoryRemovesFolder() {
        Story s = new Story("id1", "Title", "Desc");
        manager.saveStory(s);

        Path path = manager.getStoryPath("id1");
        assertTrue(Files.exists(path));

        assertTrue(manager.deleteStory("id1"));
        assertFalse(Files.exists(path));
    }

    @Test
    void loadAllStoriesReturnsAllSavedStories() {
        Story s1 = new Story("id1", "A", "Desc");
        Story s2 = new Story("id2", "B", "Desc");
        manager.saveStory(s1);
        manager.saveStory(s2);

        List<Story> all = manager.loadAllStories();

        assertEquals(2, all.size());
    }
}