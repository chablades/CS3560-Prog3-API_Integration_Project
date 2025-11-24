package com.example.AIWorldBuilder.editor.controller;

import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.core.model.Chapter;

public interface EditorControllerInterface {
    void start();
    void onGenerateContentRequest(String prompt);
    void onCancelGenerateContentRequest();
    void onExitButtonClicked();
    void onSaveButtonClicked();
    void onSettingsButtonClicked();
    void setDirty(boolean dirty);
    boolean isDirty();
    Chapter getChapterById(Story story, String chapterId);
    java.util.List<Chapter> getChapters(Story story);
    Story getStory();
    void setStory(Story story);
    Chapter getCurrentChapter();
    void setCurrentChapter(Chapter chapter);
}
