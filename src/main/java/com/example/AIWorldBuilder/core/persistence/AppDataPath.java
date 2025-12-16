package com.example.AIWorldBuilder.core.persistence;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AppDataPath {

    // Test override (null in production)
    private static Path overrideBaseDir = null;

    /** Test-only override for base directory */
    public static void overrideBaseDir(Path newBaseDir) {
        overrideBaseDir = newBaseDir;
    }

    /** Returns the base directory (test override if present) */
    public static Path getBaseDir() {
        if (overrideBaseDir != null) {
            return overrideBaseDir;
        }
        return Paths.get(System.getProperty("user.home"), ".aiworldbuilder");
    }

    public static Path getSettingsPath() {
        return getBaseDir().resolve("settings");
    }

    public static Path getStoriesPath() {
        return getBaseDir().resolve("stories");
    }

    public static Path getChaptersPath(String storyId) {
        return getStoriesPath().resolve(storyId).resolve("chapters");
    }

}