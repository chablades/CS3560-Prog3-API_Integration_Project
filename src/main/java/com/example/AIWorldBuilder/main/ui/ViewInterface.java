package com.example.AIWorldBuilder.main.ui;
import com.example.AIWorldBuilder.main.controller.Page;
import com.example.AIWorldBuilder.editor.ui.EditorPage;
import com.example.AIWorldBuilder.core.model.Story;

public interface ViewInterface {
    void showPage(Page page, Object data);
    void addStoryToMenu(Story story);
    void removeStoryById(String storyId);
    void updateStoryCard(Story story);

    default void showPage(Page page) {
        showPage(page, null);
    }

    EditorPage getEditorPage();
    void setEditorPage(EditorPage editorPage);
}
