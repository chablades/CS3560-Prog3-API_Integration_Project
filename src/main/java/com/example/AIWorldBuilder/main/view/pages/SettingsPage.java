package com.example.AIWorldBuilder.main.view.pages;

import javax.swing.*;
import java.awt.*;

import com.example.AIWorldBuilder.core.model.*;
import com.example.AIWorldBuilder.main.controller.*;

// SettingsPage class to display settings options
public class SettingsPage extends JPanel {
    
    private ControllerInterface controller;
    private AppSettings settings;
    // Constructor
    public SettingsPage(ControllerInterface controller) {
        this.controller = controller;

        // Base layout and styling
        setLayout(new BorderLayout());
        setBackground(new Color(10, 10, 10));

        refresh();
    }

    public void refresh() {

        removeAll();

        settings = controller.getAppSettings();

        // HEADER
        JLabel headerLabel = new JLabel("Settings");
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

        // API KEY ROW
        JPanel apiKeyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        apiKeyPanel.setBackground(new Color(10, 10, 10));

        JLabel apiKeyLabel = new JLabel("API Key: ");
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
        contentPanel.add(apiKeyPanel);

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
            String newApiKey = new String(apiKeyField.getPassword()).trim();
            settings.apiKey = newApiKey;
            controller.onAppSettingsUpdated(settings);
            controller.onPreviousButtonClicked();
        });
        buttonPanel.add(saveButton);

        // Refresh the UI
        revalidate();
        repaint();
    }
}
        