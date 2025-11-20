package com.example.AIWorldBuilder.view.components.common;

import javax.swing.*;

public class ConfirmDeleteDialog {
    public boolean showDialog(String storyTitle) {
        int result = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to delete the story \"" + storyTitle + "\"?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        return result == JOptionPane.YES_OPTION;
    }
}
