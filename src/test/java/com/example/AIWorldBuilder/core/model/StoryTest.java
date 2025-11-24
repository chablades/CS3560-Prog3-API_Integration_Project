package com.example.AIWorldBuilder.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StoryTest {

    @Test
    void updateAppliesStorySettings() {
        Story s = new Story("id1", "Old", "OldDesc");

        StorySettings settings = new StorySettings();
        settings.title = "New";
        settings.description = "NewDesc";

        s.update(settings);

        assertEquals("New", s.getTitle());
        assertEquals("NewDesc", s.getDescription());
    }
}