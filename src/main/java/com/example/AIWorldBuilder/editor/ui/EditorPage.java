package com.example.AIWorldBuilder.editor.ui;

import javax.swing.*;

import com.example.AIWorldBuilder.core.model.Chapter;
import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.editor.controller.EditorControllerInterface;
import com.example.AIWorldBuilder.editor.ui.components.*;
import com.example.AIWorldBuilder.editor.ui.tabs.*;

import java.awt.*;

public class EditorPage extends JPanel implements EditorViewInterface {
    private EditorControllerInterface controller;
    private Story story;

    private JTabbedPane tabbedPane;
    private ChaptersTab chapterTab;
    private ChapterAIPanel aiChatPanel;
    private Chapter currentChapter;
    
    public EditorPage(Story story) {
        this.story = story;

        // Base layout
        setLayout(new BorderLayout());
        setBackground(new Color(10, 10, 10));
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public void refresh() {

        removeAll();
        // Top bar
        TopBar topBar = new TopBar(controller);
        add(topBar, BorderLayout.NORTH);

        // Tabbed pane with dummy tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(0,0,0));
        tabbedPane.setOpaque(false);

        chapterTab = new ChaptersTab(controller);

        aiChatPanel = chapterTab.getAIChatPanel();

        tabbedPane.addTab("Chapters", chapterTab);
        add(tabbedPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    // Set controller
    @Override
    public void setController(EditorControllerInterface controller) {
        this.controller = controller;
    }
    // Set generating state in AIChatPanel
    @Override
    public void setGenerating(boolean generating) {
        // get tab and set its generating state
        if (aiChatPanel != null) {
            aiChatPanel.setGenerating(generating);
        }
    }

    // Set current chapter in ChaptersTab
    @Override
    public void openChapter(String chapterId) {
        this.currentChapter = controller.getCurrentChapter();
        chapterTab.openChapter(chapterId);
        aiChatPanel = chapterTab.getAIChatPanel();
    }

    // Exit current chapter in ChaptersTab
    @Override
    public void exitChapter() {
        chapterTab.exitChapter();
        this.currentChapter = null;
    }

    // Append token to AIChatPanel
    @Override
    public void appendToken(String message) {
        System.out.println("Appending token to chat area: " + message);
        if (aiChatPanel != null) {
            aiChatPanel.appendToken(message);
        }
    }

    // Show error message dialog
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Show general message dialog
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    // Set chat text in AIChatPanel
    @Override
    public void setChatText(String text) {
        if (aiChatPanel != null) {  
            aiChatPanel.setChatText(text);
        }
    }

    // Get chapter title from current chapter
    @Override
    public String getChapterTitle() {
        if (currentChapter != null) {
            return currentChapter.getTitle();
        }
        return "";
    }
    // Get chapter text from AIChatPanel chatArea
    @Override
    public String getChapterText() {
        if (aiChatPanel != null) {
            return aiChatPanel.getChatText();
        }
        return "";
    }  

    // Get prompt text from AIChatPanel
    @Override
    public String getPrompt() {
        if (aiChatPanel != null) {
            return aiChatPanel.getPromptText();
        }
        return "";
    }
}