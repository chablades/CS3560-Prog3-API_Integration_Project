package com.example.AIWorldBuilder.editor.ui;

import javax.swing.*;

import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.editor.controller.EditorControllerInterface;
import com.example.AIWorldBuilder.editor.ui.components.*;
import com.example.AIWorldBuilder.editor.ui.tabs.*;

import java.awt.*;

public class EditorPage extends JPanel implements EditorViewInterface {
    private EditorControllerInterface controller;
    private Story story;

    private JTabbedPane tabbedPane;
    private StoryTab storyTab;
    private AIChatPanel aiChatPanel;
    
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

        storyTab = new StoryTab(controller);
        aiChatPanel = storyTab.getAIChatPanel();

        tabbedPane.addTab("Story", storyTab);
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

    // Append token to AIChatPanel
    @Override
    public void appendToken(String message) {
        if (aiChatPanel != null) {
            aiChatPanel.appendToken(message);
        }
    }

    // Show error message dialog
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
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