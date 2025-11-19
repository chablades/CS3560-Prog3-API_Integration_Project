package com.example.AIWorldBuilder.view;

import com.example.AIWorldBuilder.Main;
import com.example.AIWorldBuilder.controller.ControllerInterface;
import com.example.AIWorldBuilder.view.pages.*;
import javax.swing.*;
import java.awt.*;

// Main application window
public class MainWindow extends JFrame implements ViewInterface {

    private JPanel cards;
    private ControllerInterface controller;
    private static final String API_KEY_PAGE = "API_KEY";
    private static final String MENU_PAGE = "MENU";
    private static final String CREATE_STORY_PAGE = "CREATE_STORY";
    private static final String SETTINGS_PAGE = "SETTINGS";

    private ApiKeyPage apiKeyPage;
    private MenuPage menuPage;
    private CreateStoryPage createStoryPage;
    private SettingsPage settingsPage;
    
    // Constructor
    public MainWindow() {
        setTitle("AI WorldBuilder");
        setSize(800, 600);
        setMinimumSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(new Color(10, 10, 10));

        cards = new JPanel(new CardLayout());
        add(cards);
    }

    // Set the controller and initialize pages
    public void setController(ControllerInterface controller) {
        this.controller = controller;

        apiKeyPage = new ApiKeyPage(controller);
        menuPage = new MenuPage(controller);
        createStoryPage = new CreateStoryPage(controller);
        settingsPage = new SettingsPage(controller);

        // Add pages to the card layout
        cards.add(apiKeyPage, API_KEY_PAGE);
        cards.add(menuPage, MENU_PAGE);
        cards.add(createStoryPage, CREATE_STORY_PAGE);
        cards.add(settingsPage, SETTINGS_PAGE);

    }

    // Show the API Key page
    @Override
    public void showApiKeyPage() {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, API_KEY_PAGE);
    }

    // Show the Menu page
    @Override
    public void showMenuPage() {
        menuPage.refresh();
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, MENU_PAGE);
    }

    // Show the Create Story page (when a new story is created)
    @Override
    public void showCreateStoryPage() {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, CREATE_STORY_PAGE);
    }

    // Show the Settings page
    @Override
    public void showSettingsPage() {
        settingsPage.refresh();
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, SETTINGS_PAGE);
    }
}
