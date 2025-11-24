package com.example.AIWorldBuilder.editor.ui.tabs;

import javax.swing.*;
import java.awt.*;

import com.example.AIWorldBuilder.core.model.Story;
import com.example.AIWorldBuilder.editor.controller.EditorControllerInterface;

public abstract class Tab extends JPanel {

    protected int BORDER_SIZE = 2;

    protected EditorControllerInterface controller;
    protected Story story;

    public Tab(EditorControllerInterface controller) {
        this.controller = controller;
        this.story = controller.getStory();
        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 20));
        setOpaque(false);
    }
    // Custom painting for rounded corners and styled border
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 20; // roundness

        int width = getWidth();
        int height = getHeight();

        // Background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width - 1, height - 1, arc, arc);

        // Border
        g2.setColor(new Color(60, 60, 60));
        g2.setStroke(new BasicStroke(BORDER_SIZE));

        g2.drawRoundRect(
            BORDER_SIZE / 2, 
            BORDER_SIZE / 2, 
            width - BORDER_SIZE, 
            height - BORDER_SIZE, 
            arc, 
            arc
        );

        g2.dispose();
    }

    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 20;
        int width = getWidth();
        int height = getHeight();

        int inset = BORDER_SIZE;
        java.awt.Shape clip = new java.awt.geom.RoundRectangle2D.Float(
            inset, 
            inset, 
            width - (inset * 2), 
            height - (inset * 2), 
            arc, 
            arc
        );

        g2.setClip(clip);

        super.paintChildren(g2);
        g2.dispose();
    }
}