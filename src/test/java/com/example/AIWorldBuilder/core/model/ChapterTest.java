package com.example.AIWorldBuilder.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChapterTest {

    @Test
    void constructorStoresFields() {
        Chapter c = new Chapter("c1", "Chapter 1", "Hello");

        assertEquals("c1", c.getChapterId());
        assertEquals("Chapter 1", c.getTitle());
        assertEquals("Hello", c.getContent());
    }

    @Test
    void settersUpdateFields() {
        Chapter c = new Chapter("c1", "Old", "OldContent");

        c.setTitle("New");
        c.setContent("NewContent");

        assertEquals("New", c.getTitle());
        assertEquals("NewContent", c.getContent());
    }
}