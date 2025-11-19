package com.example.AIWorldBuilder.persistence;

import java.io.*;
import java.nio.file.*;

public class APIKeyManager {

    private final Path keysDir = AppDataPath.getKeysPath();
    private final Path keyFile = keysDir.resolve("openai_key.txt");

    public APIKeyManager() {
        try {
            if (Files.notExists(keysDir)) {
                Files.createDirectories(keysDir);
            }
            if (Files.notExists(keyFile)) {
                Files.createFile(keyFile);
            }
        } catch (IOException e) {
            System.out.println("Failed to initialize APIKeyManager: " + e.getMessage());
        }

    }

    public boolean hasKey() {
        try {
            String key = Files.readString(keyFile).trim();
            return !key.isEmpty();
        } catch (IOException e) {
            System.out.println("Error reading API key: " + e.getMessage());
            return false;
        }
    }

    public void saveKey(String key) {
        try {
            Files.writeString(keyFile, key.trim(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error saving API key: " + e.getMessage());
        }
    }

    public String loadKey() {
        try {
            if (hasKey()) {
                return Files.readString(keyFile).trim();
            }
        }
        catch (IOException e) {
            System.out.println("Error loading API key: " + e.getMessage());
        }
        return null;
    }
    
}
