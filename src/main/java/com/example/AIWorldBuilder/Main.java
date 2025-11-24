package com.example.AIWorldBuilder;

import com.example.AIWorldBuilder.main.controller.MainControllerInterface;
import com.example.AIWorldBuilder.main.ui.MainWindow;
import com.example.AIWorldBuilder.main.controller.MainController;

public class Main {
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        MainControllerInterface controller = new MainController(window);
        window.setController(controller);
        controller.start();
        window.setVisible(true);
    }
}