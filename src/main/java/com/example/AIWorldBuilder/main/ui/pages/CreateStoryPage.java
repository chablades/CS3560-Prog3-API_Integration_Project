package com.example.AIWorldBuilder.main.ui.pages;

import javax.swing.*;
import java.awt.*;

import com.example.AIWorldBuilder.common.ui.components.PlaceholderTextArea;
import com.example.AIWorldBuilder.common.ui.components.PlaceholderTextField;
import com.example.AIWorldBuilder.main.controller.MainControllerInterface;

public class CreateStoryPage extends JPanel {
    private MainControllerInterface controller;

    public CreateStoryPage(MainControllerInterface controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(new Color(10, 10, 10));
        
        // Create header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headerPanel, BorderLayout.NORTH);

        // Page title
        JLabel pageTitle = new JLabel("Create New Story");
        pageTitle.setFont(new Font("Arial", Font.BOLD, 40));
        pageTitle.setForeground(Color.WHITE);
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(pageTitle, BorderLayout.CENTER);

        // Setting fields panel
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBackground(new Color(10, 10, 10));
        settingsPanel.setForeground(Color.WHITE);
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));
        add(settingsPanel, BorderLayout.CENTER);

        // Create optional title text field
        PlaceholderTextField titleField = new PlaceholderTextField("Enter Story Title Here (optional)");
        titleField.setBackground(new Color(30, 30, 30));
        titleField.setFont(new Font("Arial", Font.BOLD, 25));
        titleField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        settingsPanel.add(titleField, BorderLayout.NORTH);

        // Create optional description text field ()
        PlaceholderTextArea descriptionArea = new PlaceholderTextArea("Enter Story Description Here (optional)");
        descriptionArea.setBackground(new Color(30, 30, 30));
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));    
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        settingsPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create cancel button to return to menu
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            controller.onMenuButtonClicked();
        });
        buttonPanel.add(cancelButton, BorderLayout.SOUTH);

        // Create create button
        JButton createButton = new JButton("Create Story");
        createButton.setFont(new Font("Arial", Font.BOLD, 18));
        createButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            controller.onCreateStory(title, description);
        });
        buttonPanel.add(createButton, BorderLayout.NORTH);
    }
}
