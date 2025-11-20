package com.example.AIWorldBuilder.core.persistence;

import java.nio.file.*;

public class AppDataPath {
    private static final Path BASE_DIR = Path.of(System.getProperty("user.home"), ".aiworldbuilder");

    public static Path getBaseDir() {
        return BASE_DIR;
    }

    public static Path getSettingsPath() {
        return BASE_DIR.resolve("settings");
    }

    public static Path getStoriesPath() {
        return BASE_DIR.resolve("stories");
    }
}
