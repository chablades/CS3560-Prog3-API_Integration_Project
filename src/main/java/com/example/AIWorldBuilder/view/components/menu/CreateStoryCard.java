package com.example.AIWorldBuilder.view.components.menu;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import com.example.AIWorldBuilder.controller.ControllerInterface;

public class CreateStoryCard extends JPanel {

    private static final int WIDTH = 220;
    private static final int HEIGHT = 300;
    private static final int IMAGE_SIZE = 200;
    private static final int PADDING = 10;

    private ControllerInterface controller;

    public CreateStoryCard(ControllerInterface controller) {
        this.controller = controller;

        // Layout settings
        setLayout(new BorderLayout());
        setBackground(new Color(40,30,30));
        setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Change cursor on hover
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(60, 50, 50));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(40, 30, 30));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.onCreateStoryRequested();
            }
        });

        // IMAGE LABEL
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(IMAGE_SIZE, IMAGE_SIZE));
        add(imageLabel, BorderLayout.NORTH);

        // Load plus icon image
        ImageIcon plusIcon = null;
        java.net.URL imgURL = getClass().getResource("/icons/plus_icon.png");
        if (imgURL != null) {
            plusIcon = new ImageIcon(imgURL);
        } else {
            plusIcon = new ImageIcon();
            System.err.println("Warning: /icons/plus_icon.png not found.");
        }
        // Resize plus icon image
        Image img = plusIcon.getImage();
        int newWidth = IMAGE_SIZE / 2;
        int newHeight = (img.getHeight(null) * newWidth) / img.getWidth(null);
        Image resizedImg = img.getScaledInstance(newWidth, newHeight,  java.awt.Image.SCALE_SMOOTH);
        plusIcon = new ImageIcon(resizedImg);
        imageLabel.setIcon(plusIcon);

        // TEXT PANEL
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        add(textPanel, BorderLayout.SOUTH);

        // LABEL
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>CREATE NEW STORY</div></html>");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        textPanel.add(titleLabel);

    }
    
}
