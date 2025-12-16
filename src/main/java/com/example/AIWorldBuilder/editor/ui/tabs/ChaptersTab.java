package com.example.AIWorldBuilder.editor.ui.tabs;

import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.editor.controller.EditorControllerInterface;
import com.example.AIWorldBuilder.editor.ui.components.*;
import javax.swing.*;
import java.awt.*;

public class ChaptersTab extends Tab {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private ChapterListPanel chapterListPanel;
    private ChapterPanel currentChapterPanel;

    // Constructor
    public ChaptersTab(EditorControllerInterface controller) {
        super(controller);
        initializeUI(controller);
    }

    // Initialize UI components
    private void initializeUI(EditorControllerInterface controller) {
        setLayout(new BorderLayout());

        // Initialize card layout (chapters list and chapter editor)
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel, BorderLayout.CENTER);

        // Chapter list panel
        chapterListPanel = new ChapterListPanel(controller);
        cardPanel.add(chapterListPanel, "ChapterList"); 
    }

    // Pass AI chat panel to EditorPage for streaming
    public ChapterAIPanel getAIChatPanel() {
        if (currentChapterPanel == null) {
            return null;
        }
        return currentChapterPanel.getAIChatPanel();
    }

    public void openChapter(String chapterId) {
        if (currentChapterPanel != null) {
            cardPanel.remove(currentChapterPanel);
        }

        currentChapterPanel = new ChapterPanel(controller);
        cardPanel.add(currentChapterPanel, "CurrentChapter");
        cardLayout.show(cardPanel, "CurrentChapter");
    }
    
    public void exitChapter() {
        if (currentChapterPanel != null) {
            cardLayout.show(cardPanel, "ChapterList");
            cardPanel.remove(currentChapterPanel);
            currentChapterPanel = null;
            revalidate();
            repaint();
        }
    }

    
}
