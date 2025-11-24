package com.example.AIWorldBuilder.editor.ui.tabs;

import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.editor.controller.EditorControllerInterface;
import com.example.AIWorldBuilder.editor.ui.components.*;
import javax.swing.*;
import java.awt.*;

public class StoryTab extends Tab {

    public StoryTab(EditorControllerInterface controller) {
        super(controller);
        initializeUI();
    }

    // Get the AIChatPanel (second component)
    public AIChatPanel getAIChatPanel() {
        return (AIChatPanel) getComponent(1);
    }

    private void initializeUI() {
        // Left panel for story writing settings
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(40, 40, 40));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(leftPanel, BorderLayout.WEST);

        //test label on left panel
        JLabel settingsLabel = new JLabel("Story Writing Settings");
        settingsLabel.setForeground(new Color(200, 200, 200));
        leftPanel.add(settingsLabel);

        // Right panel for AI chat interface
        AIChatPanel aiChatPanel = new AIChatPanel(controller);
        add(aiChatPanel, BorderLayout.CENTER);

    }
    
}
