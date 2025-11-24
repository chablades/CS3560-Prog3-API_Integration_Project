package com.example.AIWorldBuilder.editor.ui;

import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.editor.controller.EditorControllerInterface;

public interface EditorViewInterface {
    void setController(EditorControllerInterface controller);
    void setStory(Story story);
    void refresh();
    void setGenerating(boolean generating);
    void appendToken(String token);
    void showError(String message);
    String getChapterText();
    String getPrompt();
}
