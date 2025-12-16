package com.example.AIWorldBuilder.editor.ui.components;

import javax.swing.*;
import java.awt.*;

import com.example.AIWorldBuilder.core.utils.ImageLoader;
import com.example.AIWorldBuilder.common.ui.components.ConfirmDialog;

import com.example.AIWorldBuilder.core.model.Chapter;
import com.example.AIWorldBuilder.editor.controller.*;
import com.example.AIWorldBuilder.common.ui.components.*;

// Class for a single row representing a chapter in the chapter list (includes title, trash)
public class ChapterRow extends JPanel {
    private Chapter chapter;
    private JLabel titleLabel;

    public ChapterRow(Chapter chapter, EditorControllerInterface controller) {
        this.chapter = chapter;

        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // set height to no more than 40px
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));   

        // Base row is a button to select chapter
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                controller.onChapterSelect(chapter.getChapterId());
            }
        });

        // hover effect
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(40, 40, 40));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new Color(30, 30, 30));
            }
            // click
            public void mousePressed(java.awt.event.MouseEvent evt) {
                setBackground(new Color(70, 70, 70));
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                setBackground(new Color(40, 40, 40));
            }
        });

        // Title label
        titleLabel = new JLabel(chapter.getTitle());
        titleLabel.setForeground(new Color(220, 220, 220));
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(titleLabel, BorderLayout.CENTER);

        // Chapter description (first 30 chars of content)
        String description = chapter.getSummary();
        // debug: set description to test string for now
        description = "This is a test description for the chapter row component in the chapter list panel.";
        if (description.length() > 100) {
            description = description.substring(0, 100) + "...";
        }
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setForeground(new Color(180, 180, 180));
        descriptionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        add(descriptionLabel, BorderLayout.SOUTH);

        // Trash icon button (project_root/resources/trash_icon.png)
        JButton trashButton = new JButton();
        ImageIcon trashIcon = ImageLoader.loadImage("/icons/trash_bin.png", 20, 0, null, null);
        trashButton.setIcon(trashIcon);
        trashButton.setPreferredSize(new Dimension(20, 20));
        trashButton.setBackground(new Color(30, 30, 30));
        trashButton.setBorder(BorderFactory.createEmptyBorder());
        trashButton.addActionListener(e -> {
            boolean confirm = new ConfirmDialog().showDialog(
                "Are you sure you want to delete the chapter \"" + chapter.getTitle() + "\"?",
                "Confirm Delete Chapter"
            );
            if (confirm) {
                controller.onChapterDeleteButtonClicked(chapter.getChapterId());
            }
        });
        
        add(trashButton, BorderLayout.EAST);
    }

    public Chapter getChapter() {
        return chapter;
    }
}
