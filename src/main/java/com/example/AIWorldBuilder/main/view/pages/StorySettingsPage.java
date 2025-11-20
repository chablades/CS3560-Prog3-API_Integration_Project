package com.example.AIWorldBuilder.main.view.pages;

import javax.swing.*;
import java.awt.*;

import com.example.AIWorldBuilder.core.model.*;
import com.example.AIWorldBuilder.main.controller.ControllerInterface;

public class StorySettingsPage extends JPanel {
    private ControllerInterface controller;
    private Story story;

    public StorySettingsPage(ControllerInterface controller, Story story) {
        this.controller = controller;
        this.story = story;

        // Base layout
        setLayout(new BorderLayout());
        setBackground(new Color(10, 10, 10));

        // HEADER
        JLabel headerLabel = new JLabel("Story Settings");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        // CONTENT PANEL
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);

        // TITLE ROW
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(10, 10, 10));
        JLabel titleLabel = new JLabel("Story Title: ");
        titleLabel.setForeground(Color.WHITE);
        JTextField titleField = new JTextField(30);
        titleField.setText(story.getTitle());
        titlePanel.add(titleLabel);
        titlePanel.add(titleField);
        contentPanel.add(titlePanel);

        // DESCRIPTION PANEL
        JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        descriptionPanel.setBackground(new Color(10, 10, 10));
        JLabel descriptionLabel = new JLabel("Story Description: ");
        descriptionLabel.setForeground(Color.WHITE);
        JTextArea descriptionArea = new JTextArea(5, 30);
        descriptionArea.setText(story.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(new JScrollPane(descriptionArea));
        contentPanel.add(descriptionPanel);

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.SOUTH);

        // CANCEL BUTTON
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            controller.onPreviousButtonClicked();
        });
        buttonPanel.add(cancelButton);

        // SAVE BUTTON
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            StorySettings settings = new StorySettings();
            settings.title = titleField.getText().trim();
            settings.description = descriptionArea.getText().trim();
            settings.thumbnailFilename = story.getThumbnailFilename();

            controller.onStoryUpdated(story, settings);
            controller.onPreviousButtonClicked();
        });
        buttonPanel.add(saveButton);

        // Refresh the UI
        revalidate();
        repaint();
    }
}
