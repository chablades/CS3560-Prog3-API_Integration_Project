package com.example.AIWorldBuilder.view;
import com.example.AIWorldBuilder.controller.Page;

public interface ViewInterface {
    void showPage(Page page, Object data);
    
    default void showPage(Page page) {
        showPage(page, null);
    }
}
