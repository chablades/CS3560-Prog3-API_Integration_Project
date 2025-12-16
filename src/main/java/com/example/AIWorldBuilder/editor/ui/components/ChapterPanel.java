package com.example.AIWorldBuilder.editor.ui.components;

import javax.swing.*;
import java.awt.*;

import java.util.HashMap;

import com.example.AIWorldBuilder.editor.controller.EditorControllerInterface;
import com.example.AIWorldBuilder.core.ai.PromptMode;

public class ChapterPanel extends JPanel {
    
    private ChapterAIPanel aiChatPanel;
    private JLabel modeInstructionsLabel;

    // Constructor
    public ChapterPanel(EditorControllerInterface controller) {
        initializeUI(controller);
    }

    // Initialize UI components
    private void initializeUI(EditorControllerInterface controller) {

        setLayout(new BorderLayout());
        setBackground(new Color(50, 50, 50));

        // ---------- CHAPTER TOP BAR ----------
        JPanel chapterTopBar = new JPanel();
        chapterTopBar.setLayout(new BoxLayout(chapterTopBar, BoxLayout.X_AXIS));
        chapterTopBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chapterTopBar.setBackground(new Color(35, 35, 35));

        JButton exitChapterButton = new JButton("← Back to Chapters");
        exitChapterButton.setMaximumSize(new Dimension(160, 30));
        exitChapterButton.setPreferredSize(new Dimension(160, 30));

        exitChapterButton.addActionListener(e -> {
            controller.onChapterSaveButtonClicked(); // auto-save
            controller.onChapterExitButtonClicked();
        });

        chapterTopBar.add(exitChapterButton);
        chapterTopBar.add(Box.createHorizontalGlue());

        add(chapterTopBar, BorderLayout.NORTH);

        // ---------- MAIN CONTENT ----------
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(50, 50, 50));
        add(contentPanel, BorderLayout.CENTER);

        // Left panel (settings)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(40, 40, 40));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setPreferredSize(new Dimension(220, 0));

        JLabel settingsLabel = new JLabel("Story Writing Settings");
        settingsLabel.setForeground(new Color(200, 200, 200));
        leftPanel.add(settingsLabel);

        // Change title field
        JTextField titleField = new JTextField(controller.getCurrentChapter().getTitle());
        titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        titleField.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleField.addActionListener(e -> {
            System.out.println("Title changed to: " + titleField.getText());
            controller.onChapterTitleChanged(titleField.getText());
        });

        leftPanel.add(titleField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Modes (bubble select) (Continue, Rewrite, Brainstorm)
        JPanel modeSelectionPanel = new JPanel();
        modeSelectionPanel.setLayout(new BoxLayout(modeSelectionPanel, BoxLayout.Y_AXIS));
        modeSelectionPanel.setBackground(new Color(40, 40, 40));
        modeSelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Instruction text (use JTextArea for proper wrapping)
        JTextArea modeInstructionsLabel = new JTextArea(
            "Select a mode to guide the AI:"
        );
        modeInstructionsLabel.setWrapStyleWord(true);
        modeInstructionsLabel.setLineWrap(true);
        modeInstructionsLabel.setEditable(false);
        modeInstructionsLabel.setFocusable(false);
        modeInstructionsLabel.setOpaque(false);
        modeInstructionsLabel.setForeground(new Color(180, 180, 180));
        modeInstructionsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        modeInstructionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add some spacing
        modeInstructionsLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        modeSelectionPanel.add(modeInstructionsLabel);

        // Mode descriptions
        HashMap<PromptMode, String> modeMap = new HashMap<>();
        modeMap.put(
            PromptMode.CONTINUE,
            "Mode: Continue\nThe AI will continue the story from the current point. " +
            "Prompt the AI with how you want the story to proceed."
        );
        modeMap.put(
            PromptMode.REWRITE,
            "Mode: Rewrite\nThe AI will rewrite the existing content based on your instructions. " +
            "Provide specific changes you want."
        );
        modeMap.put(
            PromptMode.BRAINSTORM,
            "Mode: Brainstorm\nThe AI will generate new ideas and suggestions based on your prompt. " +
            "Use this mode for creative input."
        );

        // Button group ensures only one selection
        ButtonGroup modeGroup = new ButtonGroup();

        for (PromptMode mode : PromptMode.values()) {
            JRadioButton modeButton = new JRadioButton(mode.toString());
            modeButton.setForeground(new Color(220, 220, 220));
            modeButton.setBackground(new Color(40, 40, 40));
            modeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            modeButton.setFocusPainted(false);

            modeButton.addActionListener(e -> {
                controller.setStrategyMode(mode);
                modeInstructionsLabel.setText(modeMap.get(mode));
            });

            modeGroup.add(modeButton);           // ✅ mutual exclusivity
            modeSelectionPanel.add(modeButton);  // add to panel
        }


        leftPanel.add(modeSelectionPanel);

        contentPanel.add(leftPanel, BorderLayout.WEST);

        // Right panel (AI editor)
        aiChatPanel = new ChapterAIPanel(controller);
        contentPanel.add(aiChatPanel, BorderLayout.CENTER);

    }  

    // Get AI chat panel
    public ChapterAIPanel getAIChatPanel() {
        // Return the AI chat panel (assumed to be the second component)
        return aiChatPanel;
    }

    public void setModeInstructions(String instructions) {
        modeInstructionsLabel.setText(instructions);
    }
}
