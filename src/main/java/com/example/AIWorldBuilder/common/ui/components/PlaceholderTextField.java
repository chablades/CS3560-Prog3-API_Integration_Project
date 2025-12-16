package com.example.AIWorldBuilder.common.ui.components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Stack;

public class PlaceholderTextField extends JTextField {

    private boolean showingPlaceholder = true;
    private boolean internalChange = false;
    private String placeholder;

    private final Stack<String> undoStack = new Stack<>();
    private final Stack<String> redoStack = new Stack<>();

    public PlaceholderTextField(String placeholder) {
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
        getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
        getActionMap().put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoStack.size() > 1) {
                    internalChange = true;
                    redoStack.push(undoStack.pop());
                    setText(undoStack.peek());
                    internalChange = false;
                }
            }
        });

        // Redo
        getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");
        getActionMap().put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!redoStack.isEmpty()) {
                    internalChange = true;
                    String text = redoStack.pop();
                    undoStack.push(text);
                    setText(text);
                    internalChange = false;
                }
            }
        });
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

    /** Force hide placeholder (e.g., before programmatic typing) */
    public void forceHidePlaceholder() {
        if (showingPlaceholder) {
            internalChange = true;
            setText("");
            setForeground(Color.WHITE);
            showingPlaceholder = false;
            internalChange = false;
        }
    }
}
