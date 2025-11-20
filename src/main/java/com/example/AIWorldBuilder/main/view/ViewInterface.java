package com.example.AIWorldBuilder.main.view;
import com.example.AIWorldBuilder.main.controller.Page;

public interface ViewInterface {
    void showPage(Page page, Object data);

    default void showPage(Page page) {
        showPage(page, null);
    }
}
