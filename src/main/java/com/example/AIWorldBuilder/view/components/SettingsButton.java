package com.example.AIWorldBuilder.view.components;

import javax.swing.*;
import java.awt.*;
import com.example.AIWorldBuilder.controller.ControllerInterface;

// Settings button with icon
public class SettingsButton extends JButton {
    
    private ControllerInterface controller; 
    private final int SIZE = 50;

    // Constructor
    public SettingsButton(ControllerInterface controller) {

        // Initialize controller
        this.controller = controller;
        
        // Load icon image from resources folder
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/settings_icon.png"));

        // Set button to 50x50
        setSize(SIZE, SIZE);
        setPreferredSize(new java.awt.Dimension(SIZE, SIZE));

        // Set scaled icon
        Image scaledImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        setIcon(scaledIcon);

        // Remove button border and background for a cleaner look
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);

        // Event listener
        addActionListener(e -> {
            controller.onSettingsButtonClicked();
        });
    }
}
