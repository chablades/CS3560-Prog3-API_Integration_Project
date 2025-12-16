package com.example.AIWorldBuilder.editor.ui;

import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.editor.controller.EditorControllerInterface;

public interface EditorViewInterface {
    void setController(EditorControllerInterface controller);
    void setStory(Story story);
    void refresh();
    void setGenerating(boolean generating);
    void openChapter(String chapterId);
    void exitChapter();
    void appendToken(String token);
    void showError(String message);
    void showMessage(String message);
    void setChatText(String text);
    String getChapterTitle();
    String getChapterText();
    String getPrompt();
}
