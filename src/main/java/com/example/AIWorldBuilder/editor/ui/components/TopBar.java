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

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setBackground(new Color(30, 30, 30));

        // ---------- LEFT PANEL (Exit + Save) ----------
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftPanel.setOpaque(false);

        Dimension buttonSize = new Dimension(80, 30);

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(buttonSize);
        exitButton.addActionListener(e -> {
            boolean confirm = new ConfirmDialog().showDialog(
                "Are you sure you want to exit the editor? Unsaved changes will be lost.",
                "Confirm Exit"
            );
            if (confirm) {
                controller.onExitButtonClicked();
            }
        });

        // Save button
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(buttonSize);
        saveButton.addActionListener(e -> {
            controller.onSaveButtonClicked();
        });

        leftPanel.add(exitButton);
        leftPanel.add(saveButton);

        add(leftPanel, BorderLayout.WEST);

        // ---------- RIGHT PANEL (Settings) ----------
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);

        SettingsButton settingsButton = new SettingsButton(30);
        settingsButton.addActionListener(e -> {
            controller.onSettingsButtonClicked();
        });

        rightPanel.add(settingsButton);
        add(rightPanel, BorderLayout.EAST);
    }

}   
