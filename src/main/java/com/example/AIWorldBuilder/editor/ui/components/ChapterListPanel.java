package com.example.AIWorldBuilder.editor.ui.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.example.AIWorldBuilder.core.model.Chapter;
import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.editor.controller.EditorControllerInterface;


// Panel in ChapterTab that lists all chapters and allows selection (only one chapter can be edited at a time)
public class ChapterListPanel extends JPanel {

    private List<Chapter> chapters;
    private Story story;
    private EditorControllerInterface controller;
    private JScrollPane chapterScrollPane;
    private JPanel chapterListContainer;

    public ChapterListPanel(EditorControllerInterface controller) {

        this.controller = controller;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(50, 50, 50));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.story = controller.getStory();
        this.chapters = controller.getChapters();

        initializeUI();
    }

    private void initializeUI() {
        removeAll();

        // Title label
        JLabel titleLabel = new JLabel("Chapters");
        titleLabel.setForeground(new Color(220, 220, 220));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Scrollpane for chapter list
        chapterScrollPane = new JScrollPane();
        chapterScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        chapterScrollPane.setPreferredSize(new Dimension(400, 300));
        chapterScrollPane.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        add(chapterScrollPane);

        // Add chapter list container to scrollpane
        chapterListContainer = new JPanel();
        chapterListContainer.setLayout(new BoxLayout(chapterListContainer, BoxLayout.Y_AXIS));
        chapterListContainer.setBackground(new Color(50, 50, 50));
        chapterScrollPane.setViewportView(chapterListContainer);

        // Refresh chapter list
        refreshChapterScrollPane();

        // Create Create Chapter button
        JButton createChapterButton = new JButton("Create New Chapter");
        createChapterButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        createChapterButton.addActionListener(e -> {  
            controller.onCreateChapterButtonClicked();
            // Refresh chapter list after creating new chapter
            this.chapters = controller.getChapters();
            refreshChapterScrollPane();
        });
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(createChapterButton);

        revalidate();
        repaint();
    }

    // List chapters using ChapterRow components
    private void refreshChapterScrollPane() {
        chapterListContainer.removeAll();
        for (Chapter chapter : chapters) {
            ChapterRow chapterRow = new ChapterRow(chapter, controller);
            chapterRow.setAlignmentX(Component.LEFT_ALIGNMENT);
            chapterListContainer.add(chapterRow);
            chapterListContainer.add(Box.createRigidArea(new Dimension(0, 5)));  
        }
    }

}
