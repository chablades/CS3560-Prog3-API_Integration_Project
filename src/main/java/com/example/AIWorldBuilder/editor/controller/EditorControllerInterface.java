package com.example.AIWorldBuilder.editor.controller;

import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.core.model.Chapter;
import com.example.AIWorldBuilder.core.ai.PromptMode;

import java.util.List;

public interface EditorControllerInterface {
    void start();
    void onGenerateContentRequest(String prompt);
    void onCancelGenerateContentRequest();
    void onExitButtonClicked();
    void onSaveButtonClicked();
    void onChapterSaveButtonClicked();
    void onChapterSelect(String chapterId);
    void onChapterDeleteButtonClicked(String chapterId);
    void onChapterExitButtonClicked();
    void onChapterTitleChanged(String newTitle);
    void onCreateChapterButtonClicked();
    void onSettingsButtonClicked();
    void setDirty(boolean dirty);
    boolean isDirty();
    Chapter getChapterById(Story story, String chapterId);
    List<Chapter> getChaptersFromPersistence();
    List<Chapter> getChapters();
    Story getStory();
    void setStory(Story story);
    void setStrategyMode(PromptMode mode);
    Chapter getCurrentChapter();
    void setCurrentChapter(Chapter chapter);
    void deleteChapterById(String chapterId);
    Chapter createNewChapter(String title);
}
