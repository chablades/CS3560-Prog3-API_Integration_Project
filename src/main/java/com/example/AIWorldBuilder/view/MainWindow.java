package com.example.AIWorldBuilder.view;

import com.example.AIWorldBuilder.Main;
import com.example.AIWorldBuilder.controller.*;
import com.example.AIWorldBuilder.view.pages.*;
import com.example.AIWorldBuilder.model.Story;
import javax.swing.*;
import java.awt.*;

// Main application window
public class MainWindow extends JFrame implements ViewInterface {

    private JPanel cards;
    private ControllerInterface controller;
    
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
    }

    // Create the given page
    public JPanel createPage(Page page, Object data) {
        switch (page) {
            case API_KEY:
                return new ApiKeyPage(controller);
            case MENU:
                return new MenuPage(controller);
            case CREATE_STORY:
                return new CreateStoryPage(controller);
            case STORY_SETTINGS:
                return new StorySettingsPage(controller, (Story) data);
            case SETTINGS:
                return new SettingsPage(controller);
            default:
                return new JPanel();
        }
    }

    // Show the specified page
    @Override
    public void showPage(Page page, Object data) {

        // Delete old page if it exists
        Component[] components = cards.getComponents();
        for (Component comp : components) {
            if (comp.isVisible()) {
                cards.remove(comp);
                break;
            }
        }
        
        // Create and add the new page
        JPanel newPage = createPage(page, data);
        cards.add(newPage, page.name());
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, page.name());

        // Refresh and repaint
        revalidate();
        repaint();

    }

}
