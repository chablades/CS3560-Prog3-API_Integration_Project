package com.example.AIWorldBuilder.core.model;

public class Chapter {
    private String chapterId;
    private String title;
    private String content;

    public Chapter(String chapterId, String title, String content) {
        this.chapterId = chapterId;
        this.title = title;
        this.content = content;
    }

    public String getChapterId() { return chapterId; }
    public void setChapterId(String chapterId) { this.chapterId = chapterId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}