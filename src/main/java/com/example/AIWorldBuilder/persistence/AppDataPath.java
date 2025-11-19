package com.example.AIWorldBuilder.persistence;

import java.nio.file.*;

public class AppDataPath {
    private static final Path BASE_DIR = Path.of(System.getProperty("user.home"), ".aiworldbuilder");

    public static Path getBaseDir() {
        return BASE_DIR;
    }

    public static Path getKeysPath() {
        return BASE_DIR.resolve("keys");
    }

    public static Path getStoriesPath() {
        return BASE_DIR.resolve("stories");
    }
}
