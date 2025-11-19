package com.example.AIWorldBuilder.view.pages;

import javax.swing.*;
import java.awt.*;
import com.example.AIWorldBuilder.controller.ControllerInterface;
import com.example.AIWorldBuilder.view.components.*;
import com.example.AIWorldBuilder.view.layouts.*;

public class MenuPage extends JPanel {
    private ControllerInterface controller;
    private static final int CARD_SPACING = 10;
    private final int TITLE_IMAGE_SIZE = 400;
    private final int SETTINGS_ICON_SIZE = 40;


    public MenuPage(ControllerInterface controller) {
        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(new Color(10, 10, 10));

        // Refresh the menu page
        refresh();

    }

    public void refresh() {
                
        removeAll();

        // HEADER PANEL
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        add(headerPanel, BorderLayout.NORTH);

        // SPACER on LEFT
        JPanel leftSpacer = new JPanel();
        leftSpacer.setPreferredSize(new Dimension(SETTINGS_ICON_SIZE + 20, SETTINGS_ICON_SIZE + 20));
        leftSpacer.setOpaque(false);
        headerPanel.add(leftSpacer, BorderLayout.WEST);

        // TITLE IMAGE LABEL
        JLabel titleImageLabel = new JLabel();
        titleImageLabel.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon titleImage = null;
        java.net.URL imgURL = getClass().getResource("/icons/title.png");

        if (imgURL != null) {
            titleImage = new ImageIcon(imgURL);
        } else {
            titleImage = new ImageIcon();
            System.err.println("Warning: /icons/title.png not found.");
        }
        
        // Resize title image
        Image img = titleImage.getImage();
        int newWidth = TITLE_IMAGE_SIZE;
        int newHeight = (img.getHeight(null) * newWidth) / img.getWidth(null);
        Image resizedImg = img.getScaledInstance(newWidth, newHeight,  java.awt.Image.SCALE_SMOOTH);
        titleImage = new ImageIcon(resizedImg);
        titleImageLabel.setIcon(titleImage);

        headerPanel.add(titleImageLabel, BorderLayout.CENTER);  

        // SETTINGS BUTTON
        JButton settingsButton = new JButton();
        settingsButton.setFocusable(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setBorderPainted(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setBackground(new Color(0, 0, 0, 0));
        settingsButton.setBorder(BorderFactory.createEmptyBorder());

        // Add padding
        settingsButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        // Load + resize icon
        ImageIcon settingsIcon = new ImageIcon(getClass().getResource("/icons/settings_icon.png"));
        Image resizedSettingsImage = settingsIcon.getImage().getScaledInstance(
                SETTINGS_ICON_SIZE,
                SETTINGS_ICON_SIZE,
                Image.SCALE_SMOOTH
        );
        settingsButton.setIcon(new ImageIcon(resizedSettingsImage));

        // Add click action
        settingsButton.addActionListener(e -> controller.onSettingsButtonClicked());

        // Add directly to header
        headerPanel.add(settingsButton, BorderLayout.EAST);

        // STORY GRID PANEL
        
        JPanel storyGridPanel = new JPanel(new WrapLayout(FlowLayout.CENTER)) {
            @Override
            public Dimension getPreferredSize() {
                Container parent = getParent();
                if (parent instanceof JViewport viewport) {
                    int w = viewport.getWidth();
                    if (w > 0) {
                        // Force width to match viewport so wrap recalculates
                        Dimension size = super.getPreferredSize();
                        return new Dimension(w, size.height);
                    }
                }
                return super.getPreferredSize();
            }
        };
        storyGridPanel.setOpaque(false);

        // Spacing between cards
        WrapLayout layout = (WrapLayout) storyGridPanel.getLayout();
        layout.setHgap(20);
        layout.setVgap(20);

        // Story grid padding
        storyGridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add "Create Story" card
        CreateStoryCard createStoryCard = new CreateStoryCard(controller);
        storyGridPanel.add(createStoryCard);
        // Populate story cards using controller
        for (var s : controller.getStories()) {
            StoryCard storyCard = new StoryCard(s, controller);
            storyGridPanel.add(storyCard);
        }

        // Scroll pane for story grid
        JScrollPane scrollPane = new JScrollPane(storyGridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);

    }
}
