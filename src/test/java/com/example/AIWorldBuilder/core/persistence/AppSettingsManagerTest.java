package com.example.AIWorldBuilder.core.persistence;

import com.example.AIWorldBuilder.core.model.AppSettings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class AppSettingsManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void saveAndLoadSettingsWorks() {
        AppDataPath.overrideBaseDir(tempDir);

        AppSettingsManager manager = new AppSettingsManager();

        AppSettings settings = new AppSettings();
        settings.apiKey = "my-key";

        manager.save(settings);

        AppSettings loaded = manager.load();

        assertNotNull(loaded);
        assertEquals("my-key", loaded.apiKey);
    }
}