package com.example.AIWorldBuilder.editor.ui.components;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

import com.example.AIWorldBuilder.common.ui.components.*;
import com.example.AIWorldBuilder.editor.controller.EditorController;
import com.example.AIWorldBuilder.editor.controller.EditorControllerInterface;
import com.example.AIWorldBuilder.core.model.Story;

public class TopBar extends JPanel {
    
    private EditorControllerInterface controller;
    private Story story;
    
    public TopBar(EditorControllerInterface controller) {
        this.controller = controller;
        this.story = controller.getStory();

        // Base layout (top)
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setBackground(new Color(30, 30, 30));
        
        // Exit button on left
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            boolean confirm = new ConfirmDialog().showDialog(
                "Are you sure you want to exit the editor? Unsaved changes will be lost.",
                "Confirm Exit"
            );
            if (confirm) {
                controller.onExitButtonClicked();
            }
        });
        add(exitButton, BorderLayout.WEST);
        // Save button next to exit
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            controller.onSaveButtonClicked();
        });
        add(saveButton, BorderLayout.CENTER);
        // Settings button on right
        SettingsButton settingsButton = new SettingsButton(30);
        settingsButton.addActionListener(e -> {
            controller.onSettingsButtonClicked();
        });
        add(settingsButton, BorderLayout.EAST);
    }
}   
