package com.example.AIWorldBuilder.main.ui;

import com.example.AIWorldBuilder.Main;
import com.example.AIWorldBuilder.common.ui.pages.SettingsPage;
import com.example.AIWorldBuilder.common.ui.pages.StorySettingsPage;
import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.editor.ui.EditorPage;
import com.example.AIWorldBuilder.editor.ui.EditorViewInterface;
import com.example.AIWorldBuilder.main.controller.*;
import com.example.AIWorldBuilder.main.ui.pages.*;

import javax.swing.*;
import java.awt.*;

// Main application window
public class MainWindow extends JFrame implements ViewInterface {

    private JPanel cards;
    private MainControllerInterface controller;
    private CardLayout cardLayout;

    private ApiKeyPage apiKeyPage;
    private MenuPage menuPage;
    private CreateStoryPage createStoryPage;
    private SettingsPage settingsPage;
    private StorySettingsPage storySettingsPage;
    private EditorPage editorPage;
    
    // Constructor
    public MainWindow() {
        setTitle("AI WorldBuilder");
        setSize(800, 600);
        setMinimumSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(new Color(10, 10, 10));

        // Card layout for different pages
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        add(cards, BorderLayout.CENTER);
    }

    // Set the controller and initialize pages
    public void setController(MainControllerInterface controller) {
        this.controller = controller;
    }

    // Show a specific page
    @Override
    public void showPage(Page page, Object data) {

        switch (page) {

            case API_KEY:
                if (apiKeyPage == null) {
                    apiKeyPage = new ApiKeyPage(controller);
                    cards.add(apiKeyPage, page.name());
                }
                break;

            case MENU:
                if (menuPage == null) {
                    menuPage = new MenuPage(controller);
                    cards.add(menuPage, page.name());
                    menuPage.loadAllStories(); // ✅ full load
                }
                break;

            case CREATE_STORY:
                if (createStoryPage == null) {
                    createStoryPage = new CreateStoryPage(controller);
                    cards.add(createStoryPage, page.name());
                }
                break;

            case SETTINGS:
                if (settingsPage == null) {
                    settingsPage = new SettingsPage(controller);
                    cards.add(settingsPage, page.name());
                }
                break;

            case STORY_SETTINGS:
                if (storySettingsPage == null) {
                    storySettingsPage = new StorySettingsPage(controller, (Story) data);
                    storySettingsPage.refresh();
                    cards.add(storySettingsPage, page.name());
                } else {
                    storySettingsPage.setStory((Story) data);
                    storySettingsPage.refresh();
                }
                break;

            case EDITOR:
                break;
        }

        cardLayout.show(cards, page.name());
    }

    // Add storycard to menu
    @Override
    public void addStoryToMenu(Story story) {
        if (menuPage != null) {
            menuPage.addStory(story);
        }

    }

    // Remove storycard from menu
    @Override
    public void removeStoryById(String storyId) {
        if (menuPage != null) {
            menuPage.removeStoryById(storyId);
        }
    }

    // Update story card in menu
    @Override
    public void updateStoryCard(Story s) {
        if (menuPage != null) {
            menuPage.updateStoryCard(s);
        }
    }

    // Get the editor page
    @Override
    public EditorPage getEditorPage() {
        return editorPage;
    }

    // Set the editor page
    @Override
    public void setEditorPage(EditorPage editorPage) {

        // remove previous editor page if exists
        if (this.editorPage != null) {
            cards.remove(this.editorPage);
        }   

        // set new editor page
        this.editorPage = editorPage;
        if (cards.getComponentCount() == 0 || !isAncestorOf(editorPage)) {
            cards.add(editorPage, Page.EDITOR.name());
        }
    }

}
