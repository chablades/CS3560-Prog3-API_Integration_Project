package com.example.AIWorldBuilder.main.ui.pages;

import javax.swing.*;
import java.awt.*;

import com.example.AIWorldBuilder.core.utils.*;
import com.example.AIWorldBuilder.main.controller.MainControllerInterface;
import com.example.AIWorldBuilder.main.ui.components.CreateStoryCard;
import com.example.AIWorldBuilder.main.ui.components.StoryCard;
import com.example.AIWorldBuilder.main.ui.layouts.*;
import com.example.AIWorldBuilder.common.ui.components.*;
import com.example.AIWorldBuilder.core.model.Story;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MenuPage extends JPanel {
    private MainControllerInterface controller;
    private static final int CARD_SPACING = 10;
    private final int TITLE_IMAGE_SIZE = 400;
    private final int SETTINGS_ICON_SIZE = 40;

    private JPanel storyGridPanel;
    private Map<String, StoryCard> storyCards = new HashMap<>();

    public MenuPage(MainControllerInterface controller) {
        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(new Color(10, 10, 10));

        // Build static parts of the UI
        buildStaticUI();

        // Refresh dynamic content
        loadAllStories();
        refresh();
    }

    // Build static UI components
    private void buildStaticUI() {
                
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
        ImageIcon titleImage = ImageLoader.loadImage("/icons/title.png", TITLE_IMAGE_SIZE, 0, Optional.empty(), Optional.of("Title"));
        
        // Resize title image
        Image img = titleImage.getImage();
        int newWidth = TITLE_IMAGE_SIZE;
        int newHeight = (img.getHeight(null) * newWidth) / img.getWidth(null);
        Image resizedImg = img.getScaledInstance(newWidth, newHeight,  java.awt.Image.SCALE_SMOOTH);
        titleImage = new ImageIcon(resizedImg);
        titleImageLabel.setIcon(titleImage);

        headerPanel.add(titleImageLabel, BorderLayout.CENTER);  

        // SETTINGS BUTTON
        SettingsButton settingsButton = new SettingsButton(SETTINGS_ICON_SIZE);
        settingsButton.addActionListener(e -> controller.onSettingsButtonClicked());
        headerPanel.add(settingsButton, BorderLayout.EAST);

        // STORY GRID PANEL
        
        storyGridPanel = new JPanel(new WrapLayout(FlowLayout.CENTER)) {
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
        
        // Scroll pane for story grid
        JScrollPane scrollPane = new JScrollPane(storyGridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Refresh story cards
    public void loadAllStories() {
        storyGridPanel.setVisible(false);

        storyGridPanel.removeAll();
        storyCards.clear();

        // Always add create card
        storyGridPanel.add(new CreateStoryCard(controller));

        for (Story s : controller.getStories()) {
            StoryCard card = new StoryCard(s, controller);
            storyCards.put(s.getStoryId(), card);
            storyGridPanel.add(card);
        }

        storyGridPanel.setVisible(true);
        storyGridPanel.revalidate();
        storyGridPanel.repaint();
    }

    // Add a new story card
    public void addStory(Story s) {
        StoryCard card = new StoryCard(s, controller);
        storyCards.put(s.getStoryId(), card);
        storyGridPanel.add(card);

        storyGridPanel.revalidate();
        storyGridPanel.repaint();
    }


    // Remove story cards
    public void removeStoryById(String storyId) {
        StoryCard card = storyCards.remove(storyId);
        if (card != null) {
            storyGridPanel.remove(card);
            storyGridPanel.revalidate();
            storyGridPanel.repaint();
        }
    }

    // Update story card
    public void updateStoryCard(Story s) {
        StoryCard card = storyCards.get(s.getStoryId());
        if (card != null) {
            System.out.println("MenuPage: Updating story card for ID: " + s.getStoryId());
            card.updateCard(s);
        }
    }

    // Refresh the page
    public void refresh() {
        revalidate();
        repaint();
    }
}
