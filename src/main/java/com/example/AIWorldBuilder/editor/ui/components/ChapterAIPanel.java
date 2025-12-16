package com.example.AIWorldBuilder.editor.ui.components;

import com.example.AIWorldBuilder.editor.controller.EditorControllerInterface;
import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.core.model.Chapter;
import com.example.AIWorldBuilder.common.ui.components.*;
import javax.swing.*;
import java.awt.*;

import com.example.AIWorldBuilder.core.ai.PromptMode;

public class ChapterAIPanel extends JPanel {
    private EditorControllerInterface controller;
    private Story story;
    private Chapter chapter;

    private JPanel chatPanel;
    private JScrollPane chatScrollPane;
    private PlaceholderTextArea chatArea;
    private PlaceholderTextField promptInput;
    private JButton sendCancelButton;

    private boolean generating = false;

    public ChapterAIPanel(EditorControllerInterface controller) {
        this.controller = controller;
        this.story = controller.getStory();
        this.chapter = controller.getCurrentChapter();

        initializeUI();

    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));

        // Request focus when panel is displayed
        SwingUtilities.invokeLater(() -> {
            requestFocusInWindow();
        });

        // Chat area panel
        chatPanel = new JPanel() {        
            // Make chat panel width match the viewport width
            @Override
            public Dimension getPreferredSize() {
                Container parent = getParent();
                if (parent instanceof JViewport viewport) {
                    int w = viewport.getWidth();
                    if (w > 0) {
                        Dimension size = super.getPreferredSize();
                        return new Dimension(w, size.height);
                    }
                }
                return super.getPreferredSize();
            }
        };
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(new Color(25, 25, 25));
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chatPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        chatScrollPane = new JScrollPane(chatPanel);
        chatScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(chatScrollPane, BorderLayout.CENTER);

        // Text area for chat messages (for demonstration purposes)
        chatArea = new PlaceholderTextArea("Welcome to the story maker! Enter a prompt and I will write a story for you!");
        if (chapter != null && chapter.getContent() != null && !chapter.getContent().isEmpty()) {
            chatArea.setRealText(chapter.getContent());
        }
        chatArea.setEditable(true);
        chatArea.setBackground(new Color(25, 25, 25));
        chatArea.setForeground(new Color(220, 220, 220));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setCaretColor(new Color(220, 220, 220));
        chatArea.getCaret().setBlinkRate(500);
       
        chatPanel.add(chatArea);

        // Prompt input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(new Color(35, 35, 35));
        add(inputPanel, BorderLayout.SOUTH);

        // Prompt input field with placeholder
        promptInput = new PlaceholderTextField("Enter your prompt here...");
        promptInput.setBackground(new Color(50, 50, 50));
        promptInput.setForeground(new Color(220, 220, 220));
        promptInput.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        inputPanel.add(promptInput, BorderLayout.CENTER);

        // Send button
        sendCancelButton = new JButton("Send");
        sendCancelButton.addActionListener(e -> {
            if (isGenerating()) {
                controller.onCancelGenerateContentRequest();
                System.out.println("Generation cancelled by user.");
                return;
            }
            else {
                controller.onGenerateContentRequest(promptInput.getText());
                System.out.println("Prompt sent: " + promptInput.getText());
            }
        });
        inputPanel.add(sendCancelButton, BorderLayout.EAST);

    }

    // Set generating and remove focus
    public void setGenerating(boolean generating) {
        this.generating = generating;
        // Enable/disable chatArea
        chatArea.setEditable(!generating);
        System.out.println("Set generating to: " + generating);

        if (generating) {
            chatArea.forceHidePlaceholder();
            chatArea.setEditable(false);
            sendCancelButton.requestFocusInWindow();
        } else {
            chatArea.setEditable(true);
            chatArea.requestFocusInWindow();
        }

        sendCancelButton.setText("" + (generating ? "Cancel" : "Send"));
    }

    // Check if AI is generating content
    public boolean isGenerating() {
        return generating;
    }

    // Append token to chat area
    public void appendToken(String message) {
        chatArea.append(message);
        chatScrollPane.getVerticalScrollBar().setValue(chatScrollPane.getVerticalScrollBar().getMaximum());
    }

    // Get chat area text
    public String getChatText() {
        return chatArea.getText();
    }

    // Get prompt input text
    public String getPromptText() {
        return promptInput.getText();
    }

    // Set chatarea text
    public void setChatText(String text) {
        chatArea.setText(text);
    }

}