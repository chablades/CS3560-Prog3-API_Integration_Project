package com.example.AIWorldBuilder.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StorySettingsTest {

    @Test
    void fieldsArePublicAndAssignable() {
        StorySettings s = new StorySettings();
        s.title = "My Title";
        s.description = "My Description";
        s.thumbnailFilename = "thumb.png";

        assertEquals("My Title", s.title);
        assertEquals("My Description", s.description);
        assertEquals("thumb.png", s.thumbnailFilename);
    }
}