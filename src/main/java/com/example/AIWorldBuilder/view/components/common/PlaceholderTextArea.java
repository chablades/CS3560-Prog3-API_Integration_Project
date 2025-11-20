package com.example.AIWorldBuilder.view.components.common;

import javax.swing.*;
import java.awt.*;

public class PlaceholderTextArea extends JTextArea {

    private boolean showingPlaceholder = true;
    private String placeholder;
    
    public PlaceholderTextArea(String placeholder) {
        this.placeholder = placeholder;
        setText(placeholder);
        setForeground(Color.GRAY);

        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (showingPlaceholder) {
                    setText("");
                    setForeground(Color.WHITE);
                    showingPlaceholder = false;
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (getRealText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                    showingPlaceholder = true;
                }
            }
        });
    }

    // Method to get the actual text without placeholder consideration
    public String getRealText() {
        return super.getText();
    }
    // Override getText to return empty string if placeholder is showing
    @Override
    public String getText() {
        if (showingPlaceholder) {
            return "";
        } else {
            return super.getText();
        }
    }
}