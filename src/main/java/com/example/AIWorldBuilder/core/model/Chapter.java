package com.example.AIWorldBuilder.core.model;

// Represents a chapter in the AI World Builder application
public class Chapter {
    private String chapterId;
    private String title;
    private String content;
    private String summary;
    private boolean summaryStale;

    public Chapter(String chapterId, String title, String content) {
        this.chapterId = chapterId;
        this.title = title;
        this.content = content;
        this.summary = "";
        this.summaryStale = true;
    }
    
    // Getters and Setters

    public String getChapterId() { return chapterId; }
    public void setChapterId(String chapterId) { this.chapterId = chapterId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { 
        this.content = content;
        this.summaryStale = true;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
        this.summaryStale = false;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapterId='" + chapterId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", summary='" + summary + '\'' +
                ", summaryStale=" + summaryStale +
                '}';
    }
}