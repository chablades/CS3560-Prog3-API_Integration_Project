package com.example.AIWorldBuilder.persistence;

import java.io.IOException;
import java.nio.file.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.AIWorldBuilder.model.Story;
import java.util.ArrayList;
import java.util.List;

public class StoryManager {
    private final Path storiesDir = AppDataPath.getStoriesPath();

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public StoryManager() {
        try {
            if (Files.notExists(storiesDir)) {
                Files.createDirectories(storiesDir);
            }
        } catch (IOException e) {
            System.out.println("Failed to initialize StoryManager: " + e.getMessage());
        }
    }

    public Path createStoryFolder(String storyId) {
        Path storyDir = storiesDir.resolve(storyId);
        try {
            if (Files.notExists(storyDir)) {
                Files.createDirectories(storyDir);

                // Create subfolders
                Files.createDirectories(storyDir.resolve("chapters"));

                return storyDir;
            }
        } catch (IOException e) {
            System.out.println("Error creating story folder: " + e.getMessage());
        }
        return null;    
    }

    public void saveStory(Story story) {
        Path storyDir = storiesDir.resolve(story.getStoryId());
        if (Files.exists(storyDir)) {
            try {
                String storyJson = gson.toJson(story);
                Files.writeString(storyDir.resolve("story.json"),
                    storyJson, 
                    StandardOpenOption.CREATE, 
                    StandardOpenOption.TRUNCATE_EXISTING
                );
            } catch (IOException e) {
                System.out.println("Error saving story: " + e.getMessage());
            }
        } else {
            System.out.println("Story directory does not exist for story ID: " + story.getStoryId());
        }
    }
    
    public Story loadStory(String storyId) {
        Path storyDir = storiesDir.resolve(storyId);
        if (Files.exists(storyDir)) {
            try {
                String storyJson = Files.readString(storyDir.resolve("story.json"));
                return gson.fromJson(storyJson, Story.class);
            } catch (IOException e) {
                System.out.println("Error loading story: " + e.getMessage());
            }
        } else {
            System.out.println("Story directory does not exist for story ID: " + storyId);
        }
        return null;
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
