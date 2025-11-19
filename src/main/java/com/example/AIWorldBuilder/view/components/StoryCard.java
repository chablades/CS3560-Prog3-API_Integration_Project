package com.example.AIWorldBuilder.view.components;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import com.example.AIWorldBuilder.model.Story;
import com.example.AIWorldBuilder.controller.ControllerInterface;

public class StoryCard extends JPanel {

    private static final int IMAGE_SIZE = 200;
    private static final int PADDING = 10;
    private static final int DESCRIPTION_LIMIT = 100;

    private String title;
    private String description;

    private ControllerInterface controller;
    private Story story;

    public StoryCard(Story story, ControllerInterface controller) {
        this.story = story;
        this.controller = controller;

        // Layout settings
        setLayout(new BorderLayout());
        setBackground(new Color(30,30,30));
        setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        setPreferredSize(new Dimension(IMAGE_SIZE + 2 * PADDING, IMAGE_SIZE + (2 * PADDING) + 100));

        // Change cursor on hover
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(50, 50, 50));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(30, 30, 30));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("StoryCard clicked: " + story.getTitle());
            }
        });

        // IMAGE LABEL
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(IMAGE_SIZE, IMAGE_SIZE));
        add(imageLabel, BorderLayout.NORTH);

        // Load thumbnail image
        Path thumbnailPath = controller.getStoryPath(story.getStoryId()).resolve(story.getThumbnailFilename());
        ImageIcon thumbnail = loadThumbnail(thumbnailPath.toString());
        imageLabel.setIcon(thumbnail);

        // TEXT PANEL
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        add(textPanel, BorderLayout.SOUTH);

        // TITLE LABEL
        JLabel titleLabel = new JLabel(story.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        textPanel.add(titleLabel);

        // DESCRIPTION LABEL
        String desc = story.getDescription();
        if (desc.length() > DESCRIPTION_LIMIT) {
            desc = desc.substring(0, DESCRIPTION_LIMIT) + "...";
        }
        JLabel descriptionLabel = new JLabel("<html><p style=\"width:200px;\">" + desc + "</p></html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionLabel.setForeground(Color.LIGHT_GRAY);
        textPanel.add(descriptionLabel);
    }

    private ImageIcon loadThumbnail(String path) {
        System.out.println("path: " + path);
        try {
            if (path != null && !path.isBlank() && Files.exists(Path.of(path))) {
                ImageIcon thumbnail = new ImageIcon(path);
                Image scaledThumbnail = thumbnail.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledThumbnail);
            }
        } catch (Exception ignored) {}

        ImageIcon thumbnail = new ImageIcon(getClass().getResource("/icons/thumbnail_placeholder.png"));
        Image scaledThumbnail = thumbnail.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledThumbnail);
    }
    
}
