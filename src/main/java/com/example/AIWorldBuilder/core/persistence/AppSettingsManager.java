package com.example.AIWorldBuilder.core.persistence;

import java.io.*;
import java.nio.file.*;

import com.example.AIWorldBuilder.core.model.AppSettings;
import com.example.AIWorldBuilder.core.utils.FileUtils;

public class AppSettingsManager {

    private final Path settingsPath = AppDataPath.getSettingsPath();
    private final Path settingsFile = settingsPath.resolve("settings.json");

    public AppSettingsManager() {
        FileUtils.ensureDir(settingsPath);
        FileUtils.ensureFile(settingsFile);
    }
    
    // Save settings to file
    public void save(AppSettings settings) {
        FileUtils.writeJson(settingsFile, settings);
    }

    // Load the settings to a new AppSettings object
    public AppSettings load() {
        AppSettings settings = FileUtils.readJson(settingsFile, AppSettings.class);
        if (settings == null) {
            settings = new AppSettings();
            save(settings);
        }
        return settings;
    }

    // Check if theres an api key saved
    public boolean hasApiKey() {
        AppSettings settings = load();
        return settings.apiKey != null && !settings.apiKey.isEmpty();
    }
}
