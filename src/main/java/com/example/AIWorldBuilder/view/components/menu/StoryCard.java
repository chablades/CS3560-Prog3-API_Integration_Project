package com.example.AIWorldBuilder.view.components.menu;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import com.example.AIWorldBuilder.model.Story;
import com.example.AIWorldBuilder.controller.ControllerInterface;
import com.example.AIWorldBuilder.utils.*;
import com.example.AIWorldBuilder.view.components.common.ConfirmDeleteDialog;

import java.util.Optional;


public class StoryCard extends JPanel {

    private static final int WIDTH = 220;
    private static final int HEIGHT = 300;
    private static final int IMAGE_SIZE = 200;
    private static final int DESCRIPTION_LIMIT = 100;
    private static final int BUTTON_SIZE = 24;

    private String title;
    private String description;

    private ControllerInterface controller;
    private Story story;

    public StoryCard(Story story, ControllerInterface controller) {
        this.story = story;
        this.controller = controller;

        // Layout settings
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Overlay panel
        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(new OverlayLayout(overlayPanel));
        overlayPanel.setOpaque(false);
        overlayPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        overlayPanel.setAlignmentX(0.0f);
        overlayPanel.setAlignmentY(0.0f);

        add(overlayPanel, BorderLayout.CENTER);

        // Main content panel
        JPanel mainPanel = new JPanel(null);
        mainPanel.setOpaque(false);
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        mainPanel.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        mainPanel.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        mainPanel.setAlignmentX(0.0f);
        mainPanel.setAlignmentY(0.0f);

        // Main content mouse listener
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(50, 40, 40));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(30, 30, 30));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Story clicked: " + story.getTitle());
            }
        });

        // Image label
        JLabel imageLabel = new JLabel();
        int pad = 10;
        imageLabel.setBounds(
            ((WIDTH - IMAGE_SIZE) / 2),  // centered left–right
            pad,                         // add top padding
            IMAGE_SIZE,
            IMAGE_SIZE
        );

        Path thumbnailPath = controller.getStoryPath(story.getStoryId()).resolve(story.getThumbnailFilename());
        ImageIcon thumbnail = ImageLoader.loadImage(thumbnailPath.toString(), 
                                                    IMAGE_SIZE, IMAGE_SIZE, 
                                                    Optional.of("/icons/thumbnail_placeholder.png"), 
                                                    Optional.of("Thumbnail"));
        imageLabel.setIcon(thumbnail);

        mainPanel.add(imageLabel);

        // Title label
        JLabel titleLabel = new JLabel(story.getTitle());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(10, IMAGE_SIZE + 10, WIDTH - 20, 22);
        mainPanel.add(titleLabel);

        // Description area
        JTextArea descriptionArea = new JTextArea(story.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setFocusable(false);
        descriptionArea.setOpaque(false);
        descriptionArea.setForeground(Color.LIGHT_GRAY);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionArea.setBounds(10, IMAGE_SIZE + 10 + 26, WIDTH - 20, 80);
        mainPanel.add(descriptionArea);

        overlayPanel.add(mainPanel);


        // Button panel on top
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(0.0f);
        buttonPanel.setAlignmentY(0.0f);

        // Float top-right
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        
        // Delete button
        JButton deleteBtn = new JButton();
        deleteBtn.setFocusable(false);
        deleteBtn.setContentAreaFilled(false);
        deleteBtn.setBorderPainted(false);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setOpaque(false);

        ImageIcon trashBinIcon = ImageLoader.loadImage("/icons/trash_bin.png", BUTTON_SIZE, BUTTON_SIZE, Optional.empty(), Optional.of("Settings"));
        deleteBtn.setIcon(trashBinIcon);

        deleteBtn.addActionListener(e -> {
            ConfirmDeleteDialog confirmDialog = new ConfirmDeleteDialog();
            boolean confirmed = confirmDialog.showDialog(story.getTitle());
            if (confirmed) {
                controller.onDeleteStory(story.getStoryId());
            }
        });
        

        // Story Settings button
        JButton settingsBtn = new JButton();
        settingsBtn.setFocusable(false);
        settingsBtn.setContentAreaFilled(false);
        settingsBtn.setBorderPainted(false);
        settingsBtn.setFocusPainted(false);
        settingsBtn.setOpaque(false);

        ImageIcon threeDotsIcon = ImageLoader.loadImage("/icons/three_dots.png", BUTTON_SIZE, BUTTON_SIZE, Optional.empty(), Optional.of("Settings"));

        settingsBtn.addActionListener(e -> {
            controller.onStorySettingsButtonClicked(story);
        });

        settingsBtn.setIcon(threeDotsIcon);
        


        // Add buttons to buttonPanel
        buttonPanel.add(deleteBtn);
        buttonPanel.add(settingsBtn);


        // Add to overlay
        overlayPanel.add(buttonPanel);  // TOP
        overlayPanel.add(mainPanel);    // BELOW

    }
    
}
