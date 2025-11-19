package com.example.AIWorldBuilder.model;

import java.util.List;
import java.util.ArrayList;

// Model class representing a story
public class Story {
    private String storyId;
    private String title;
    private String description;
    private String thumbnailFilename;
    private long dateCreated;
    private long dateModified;
    private List<String> chapterIds;

    public Story() {
        this.chapterIds = new ArrayList<>();
    }
    
    public Story(String storyId, String title, String description) {
        this();
        this.storyId = storyId;
        this.title = title;
        this.description = description;
        this.thumbnailFilename = "thumbnail.png";
        this.dateCreated = System.currentTimeMillis();
        this.dateModified = System.currentTimeMillis();
    }

    public String getStoryId() { return storyId; }
    public void setStoryId(String storyId) { this.storyId = storyId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getThumbnailFilename() { return thumbnailFilename; }
    public void setThumbnailFilename(String thumbnailFilename) { this.thumbnailFilename = thumbnailFilename; }
    public long getDateCreated() { return dateCreated; }

    public long getDateModified() { return dateModified; }
    public void setDateModified(long dateModified) { this.dateModified = dateModified; }

    public List<String> getChapterIds() { return chapterIds; }
    public void addChapterId(String chapterId) { this.chapterIds.add(chapterId); }
    public void removeChapterId(String chapterId) { this.chapterIds.remove(chapterId); }
}
