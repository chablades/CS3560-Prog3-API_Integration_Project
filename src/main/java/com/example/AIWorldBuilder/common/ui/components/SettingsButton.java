package com.example.AIWorldBuilder.common.ui.components;

import javax.swing.*;
import java.awt.*;

import com.example.AIWorldBuilder.core.utils.ImageLoader;
import com.example.AIWorldBuilder.main.controller.MainControllerInterface;

import java.util.Optional;

// Settings button with icon
public class SettingsButton extends JButton {
    
    private MainControllerInterface controller; 
    private int size;

    // Constructor
    public SettingsButton(int size) {

        this.size = size;

        setFocusable(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setBackground(new Color(0, 0, 0, 0));
        setBorder(BorderFactory.createEmptyBorder());

        // Add padding
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        // Load + resize icon
        ImageIcon settingsIcon = new ImageIcon(getClass().getResource("/icons/settings_icon.png"));
        Image resizedSettingsImage = settingsIcon.getImage().getScaledInstance(
                size,
                size,
                Image.SCALE_SMOOTH
        );
        setIcon(new ImageIcon(resizedSettingsImage));
    }
}
