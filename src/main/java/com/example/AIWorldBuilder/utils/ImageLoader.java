package com.example.AIWorldBuilder.utils;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.awt.image.BufferedImage;

// Utility class for loading and scaling images, maintaining aspect ratios, handling fallbacks
public class ImageLoader {
    public static ImageIcon loadImage(String path,
                                    int width,
                                    int height, 
                                    Optional<String> fallbackPlaceholderFile, 
                                    Optional<String> fallbackPlaceholderText) {

        Image img = tryLoadImage(path);

        // Try fallback file if main load failed
        if (img == null && fallbackPlaceholderFile.isPresent()) {
            img = tryLoadImage(fallbackPlaceholderFile.get());
        }

        // If still nothing → fallback text
        if (img == null) {
            String text = fallbackPlaceholderText.orElse("");
            return makeTextIcon(text, width, height);
        }

        Dimension fixedDim = fixDimensions(img, width, height);
        Image scaled = img.getScaledInstance(fixedDim.width, fixedDim.height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
        
    }

    private static Image tryLoadImage(String path) {
        try {
            if (path != null && !path.isBlank() && Files.exists(Path.of(path))) {
                return new ImageIcon(path).getImage();
            }
        } catch (Exception ignored) {}

        // Try resource path
        try {
            return new ImageIcon(ImageLoader.class.getResource(path)).getImage();
        } catch (Exception ignored) {}

        return null;
    }

    private static ImageIcon makeTextIcon(String text, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        // Fill background
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, width, height);

        // Draw centered text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, height / 5));
        FontMetrics fm = g2d.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        int y = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(text, x, y);

        g2d.dispose();
        return new ImageIcon(img);
    }

    // Function to fix dimensions, maintaining aspect ratio if needed
    public static Dimension fixDimensions(Image img, int width, int height) {
        int originalWidth = img.getWidth(null);
        int originalHeight = img.getHeight(null);

        // Invalid original dimensions
        if (originalWidth <= 0 || originalHeight <= 0) {
            return new Dimension(width, height);
        }

        // Width and height == 0, use original size
        if (width <= 0 && height <= 0) {
            return new Dimension(originalWidth, originalHeight);
        }

        // If width is 0, calculate based on height
        if (width <= 0) {
            double aspectRatio = (double) originalWidth / originalHeight;
            width = (int) (height * aspectRatio);
        }
        // If height is 0, calculate based on width
        else if (height <= 0) {
            double aspectRatio = (double) originalHeight / originalWidth;
            height = (int) (width * aspectRatio);
        }
        return new Dimension(width, height);
    }

}
