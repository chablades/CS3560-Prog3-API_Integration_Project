package com.example.AIWorldBuilder.main.ui.pages;

import javax.swing.*;
import java.awt.*;

import com.example.AIWorldBuilder.core.model.*;
import com.example.AIWorldBuilder.main.controller.MainControllerInterface;

public class ApiKeyPage extends JPanel {
    private MainControllerInterface controller;
    private AppSettings settings;

    public ApiKeyPage(MainControllerInterface controller) {
        this.controller = controller;
        this.settings = controller.getAppSettings();

        // Base Layout and Styling
        setLayout(new BorderLayout());
        setBackground(new Color(10, 10, 10));

        // Header
        JLabel headerLabel = new JLabel("Enter Your OpenAI API Key");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(10, 10, 10));
        add(contentPanel, BorderLayout.CENTER);

        // API KEY ROW
        JPanel apiKeyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        apiKeyPanel.setBackground(new Color(10, 10, 10));
        JLabel apiKeyLabel = new JLabel("OpenAI API Key: ");
        apiKeyLabel.setForeground(Color.WHITE);
        JPasswordField apiKeyField = new JPasswordField(30);
        apiKeyField.setText(settings.apiKey);
        apiKeyPanel.add(apiKeyLabel);
        apiKeyPanel.add(apiKeyField);

        JButton toggleVisibilityButton = new JButton("Show");
        toggleVisibilityButton.addActionListener(e -> {
            if (apiKeyField.getEchoChar() != (char) 0) {
                apiKeyField.setEchoChar((char) 0);
                toggleVisibilityButton.setText("Hide");
            } else {
                apiKeyField.setEchoChar('•');
                toggleVisibilityButton.setText("Show");
            }
        });
        apiKeyPanel.add(toggleVisibilityButton);
        contentPanel.add(apiKeyPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(10, 10, 10));
        bottomPanel.setOpaque(false);
        add(bottomPanel, BorderLayout.SOUTH);

        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(120, 35));
        saveButton.addActionListener(e -> {
            String newApiKey = new String(apiKeyField.getPassword()).trim();
            settings.apiKey = newApiKey;
            controller.onAppSettingsUpdated(settings);
            controller.onMenuButtonClicked();
        });
        bottomPanel.add(saveButton);
        
    }
}
