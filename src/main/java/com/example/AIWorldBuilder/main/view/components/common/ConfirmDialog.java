package com.example.AIWorldBuilder.main.view.components.common;

import javax.swing.*;

public class ConfirmDialog {
    public boolean showDialog(String text, String title) {
        int result = JOptionPane.showConfirmDialog(
            null,
            text,
            title,
            JOptionPane.YES_NO_OPTION
        );
        return result == JOptionPane.YES_OPTION;
    }
}
