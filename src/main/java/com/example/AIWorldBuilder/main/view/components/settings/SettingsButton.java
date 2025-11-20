package com.example.AIWorldBuilder.main.view.components.settings;

import javax.swing.*;
import java.awt.*;

import com.example.AIWorldBuilder.core.utils.ImageLoader;
import com.example.AIWorldBuilder.main.controller.ControllerInterface;

import java.util.Optional;

// Settings button with icon
public class SettingsButton extends JButton {
    
    private ControllerInterface controller; 
    private final int SIZE = 50;

    // Constructor
    public SettingsButton(ControllerInterface controller) {

        // Initialize controller
        this.controller = controller;
        
        // Load icon image from resources folder
        ImageIcon icon = ImageLoader.loadImage("/icons/settings_icon.png", 24, 24, Optional.empty(), Optional.of("Settings"));
        setIcon(icon);

        // Set button to 50x50
        setSize(SIZE, SIZE);
        setPreferredSize(new java.awt.Dimension(SIZE, SIZE));


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
