package com.example.AIWorldBuilder.persistence;

import java.io.IOException;
import java.nio.file.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.AIWorldBuilder.utils.FileUtils;
import com.example.AIWorldBuilder.model.Story;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class StoryManager {
    private final Path storiesDir = AppDataPath.getStoriesPath();


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

    public Path getStoryPath(String storyId) {
        return storiesDir.resolve(storyId);
    }

    public List<Story> loadAllStories() {
        List<Story> stories = new ArrayList<>();
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
        return stories;
    }
}
