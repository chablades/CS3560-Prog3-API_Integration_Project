package com.example.AIWorldBuilder;

import com.example.AIWorldBuilder.controller.ControllerInterface;
import com.example.AIWorldBuilder.controller.MainController;
import com.example.AIWorldBuilder.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        ControllerInterface controller = new MainController(window);
        window.setController(controller);
        controller.start();
        window.setVisible(true);
    }
}