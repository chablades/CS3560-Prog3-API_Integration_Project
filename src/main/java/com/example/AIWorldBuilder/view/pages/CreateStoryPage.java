package com.example.AIWorldBuilder.view.pages;

import javax.swing.*;
import java.awt.*;
import com.example.AIWorldBuilder.controller.ControllerInterface;
import com.example.AIWorldBuilder.view.components.*;

public class CreateStoryPage extends JPanel {
    private ControllerInterface controller;

    public CreateStoryPage(ControllerInterface controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(new Color(10, 10, 10));
        
        // Create page title
        JLabel pageTitle = new JLabel("Create New Story");
        pageTitle.setFont(new Font("Arial", Font.BOLD, 24));
        pageTitle.setForeground(Color.WHITE);
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(pageTitle, BorderLayout.PAGE_START);

        // Input fields panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(new Color(10, 10, 10));
        inputPanel.setForeground(Color.WHITE);
        add(inputPanel, BorderLayout.CENTER);

        // Create optional title text field
        PlaceholderTextField titleField = new PlaceholderTextField("Enter Story Title Here (optional)");
        titleField.setBackground(new Color(30, 30, 30));
        inputPanel.add(titleField, BorderLayout.NORTH);

        // Create optional description text field ()
        PlaceholderTextArea descriptionArea = new PlaceholderTextArea("Enter Story Description Here (optional)");
        descriptionArea.setBackground(new Color(30, 30, 30));
        inputPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create create button
        JButton createButton = new JButton("Create Story");
        createButton.setFont(new Font("Arial", Font.BOLD, 18));
        createButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            controller.onCreateStory(title, description);
        });
        buttonPanel.add(createButton, BorderLayout.NORTH);

        // Create cancel button to return to menu
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            controller.onMenuButtonClicked();
        });
        buttonPanel.add(cancelButton, BorderLayout.SOUTH);
    }
}
