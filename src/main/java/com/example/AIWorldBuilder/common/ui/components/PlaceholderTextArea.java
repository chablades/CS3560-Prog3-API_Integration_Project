package com.example.AIWorldBuilder.common.ui.components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Stack;
import java.awt.event.KeyEvent;

public class PlaceholderTextArea extends JTextArea {

    private boolean showingPlaceholder = true;
    private boolean internalChange = false;
    private String placeholder;

    private final Stack<String> undoStack = new Stack<>();
    private final Stack<String> redoStack = new Stack<>();

    private Action undoAction;
    private Action redoAction;

    private boolean isEnabled = true;

    public PlaceholderTextArea(String placeholder) {
        this.placeholder = placeholder;

        showPlaceholder();

        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (showingPlaceholder) {
                    internalChange = true;
                    setText("");
                    setForeground(Color.WHITE);
                    showingPlaceholder = false;
                    internalChange = false;
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (getText().isEmpty()) {
                    showPlaceholder();
                }
            }
        });

        undoStack.push("");

        getDocument().addDocumentListener(new DocumentListener() {
            private void record() {
                if (internalChange || showingPlaceholder) return;
                undoStack.push(getText());
                redoStack.clear();
            }

            @Override public void insertUpdate(DocumentEvent e) { record(); }
            @Override public void removeUpdate(DocumentEvent e) { record(); }
            @Override public void changedUpdate(DocumentEvent e) {}
        });

        // Undo
        undoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isEnabled) return; 

                if (undoStack.size() > 1) {
                    internalChange = true;
                    redoStack.push(undoStack.pop());
                    setText(undoStack.peek());
                    internalChange = false;
                }
            }
        };

        redoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!isEnabled) return; 

                if (!redoStack.isEmpty()) {
                    internalChange = true;
                    String text = redoStack.pop();
                    undoStack.push(text);
                    setText(text);
                    internalChange = false;
                }
            }
        };

        getActionMap().put("Undo", undoAction);
        getActionMap().put("Redo", redoAction);

        setUndoRedoKeys(true);
}


    private void showPlaceholder() {
        internalChange = true;
        setText(placeholder);
        setForeground(Color.GRAY);
        showingPlaceholder = true;
        internalChange = false;
    }

    /** Returns only user-entered text (never placeholder) */
    public String getRealText() {
        return showingPlaceholder ? "" : super.getText();
    }

    /** Programmatically set user text (safe, no undo pollution) */
    public void setRealText(String text) {
        internalChange = true;

        if (text == null || text.isBlank()) {
            showPlaceholder();
        } else {
            setText(text);
            setForeground(Color.WHITE);
            showingPlaceholder = false;

            undoStack.clear();
            redoStack.clear();
            undoStack.push(text);
        }

        internalChange = false;
    }

    /** Update placeholder text */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        if (showingPlaceholder) {
            showPlaceholder();
        }
    }

    // Force hide placeholder (e.g., when generation starts)
    public void forceHidePlaceholder() {
        if (showingPlaceholder) {
            internalChange = true;
            setText("");
            setForeground(Color.WHITE);
            showingPlaceholder = false;
            internalChange = false;
        }
    }

    // Set enabled state
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        this.isEnabled = enabled;

        if (undoAction != null) undoAction.setEnabled(enabled);
        if (redoAction != null) redoAction.setEnabled(enabled);

        if (!enabled) {
            setUndoRedoKeys(false);
            forceHidePlaceholder();
        } else {
            setUndoRedoKeys(true);
            if (getText().isEmpty()) {
                showPlaceholder();
            }
        }
    }


    // Helper to set keys for undo/redo
    private void setUndoRedoKeys(boolean enable) {
        int[] maps = {
            JComponent.WHEN_FOCUSED,
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT,
            JComponent.WHEN_IN_FOCUSED_WINDOW
        };

        for (int map : maps) {
            InputMap im = getInputMap(map);

            if (enable) {
                im.put(KeyStroke.getKeyStroke("control Z"), "Undo");
                im.put(KeyStroke.getKeyStroke("control Y"), "Redo");
                im.put(KeyStroke.getKeyStroke("meta Z"), "Undo");
                im.put(KeyStroke.getKeyStroke("meta Y"), "Redo");
            } else {
                im.remove(KeyStroke.getKeyStroke("control Z"));
                im.remove(KeyStroke.getKeyStroke("control Y"));
                im.remove(KeyStroke.getKeyStroke("meta Z"));
                im.remove(KeyStroke.getKeyStroke("meta Y"));
            }
        }
    }

}
