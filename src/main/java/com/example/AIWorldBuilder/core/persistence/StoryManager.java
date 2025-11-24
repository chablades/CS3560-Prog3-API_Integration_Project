package com.example.AIWorldBuilder.core.persistence;

import java.io.IOException;
import java.nio.file.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.core.model.Chapter;
import com.example.AIWorldBuilder.core.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class StoryManager {
    private final Path storiesDir = AppDataPath.getStoriesPath();
    private List<Story> stories = null;

    public StoryManager() {
        FileUtils.ensureDir(storiesDir);
    }

    public Path createStoryFolder(String storyId) {
        Path storyDir = storiesDir.resolve(storyId);
        try {
            Files.createDirectories(storyDir);
            return storyDir;
        } catch (IOException e) {
            System.out.println("Error creating story folder: " + e.getMessage());
            return null;
        }
    }

    public void saveStory(Story story) {
        Path storyDir = storiesDir.resolve(story.getStoryId());
        FileUtils.ensureDir(storyDir);
        Path storyFile = storyDir.resolve("story.json");
        FileUtils.writeJson(storyFile, story);
    }
    
    public Story loadStory(String storyId) {
        Path storyDir = storiesDir.resolve(storyId);
        Path storyFile = storyDir.resolve("story.json");
        return FileUtils.readJson(storyFile, Story.class);
    }

    // Delete a story by its ID
    public boolean deleteStory(String storyId) {
        Path storyDir = storiesDir.resolve(storyId);
        try {
            FileUtils.deleteDirectoryRecursively(storyDir);
            return true;
        } catch (IOException e) {
            System.out.println("Error deleting story: " + e.getMessage());
            return false;
        }
    }

    // Get the path to a specific story
    public Path getStoryPath(String storyId) {
        return storiesDir.resolve(storyId);
    }

    // Load all stories from the stories directory
    public List<Story> loadAllStories() {
        if (stories != null) {
            System.out.println("Total stories loaded: " + stories.size());
            return new ArrayList<>(stories);
        }

        stories = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(storiesDir)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    Story story = loadStory(entry.getFileName().toString());
                    if (story != null) {
                        stories.add(story);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading all stories: " + e.getMessage());
        }

        return new ArrayList<>(stories);
    }

    // Get chapters path for a specific story
    public Path getChaptersPath(String storyId) {
        return AppDataPath.getChaptersPath(storyId);
    }

    // Add a chapter to a story
    public void addChapter(Story story, Chapter chapter) {

        story.addChapterId(chapter.getChapterId());

        // add chapter to chapters with name <chapterId>.json
        Path chaptersPath = getChaptersPath(story.getStoryId());
        FileUtils.ensureDir(chaptersPath);
        Path chapterFile = chaptersPath.resolve(chapter.getChapterId() + ".json");
        FileUtils.writeJson(chapterFile, chapter);
    }

    // Remove a chapter from a story
    public void removeChapter(Story story, String chapterId) {
        story.removeChapterById(chapterId);
        Path chaptersPath = getChaptersPath(story.getStoryId());
        Path chapterFile = chaptersPath.resolve(chapterId + ".json");
        try {
            Files.deleteIfExists(chapterFile);
        } catch (IOException e) {
            System.out.println("Error deleting chapter file: " + e.getMessage());  
        }
    }

    // Load a chapter from a story
    public Chapter loadChapter(String storyId, String chapterId) {
        Path chaptersPath = getChaptersPath(storyId);
        Path chapterFile = chaptersPath.resolve(chapterId + ".json");
        return FileUtils.readJson(chapterFile, Chapter.class);
    }

    // Save a chapter
    public void saveChapter(String storyId, Chapter chapter) {
        Path chaptersPath = getChaptersPath(storyId);
        FileUtils.ensureDir(chaptersPath);
        Path chapterFile = chaptersPath.resolve(chapter.getChapterId() + ".json");
        FileUtils.writeJson(chapterFile, chapter);
    }


}
