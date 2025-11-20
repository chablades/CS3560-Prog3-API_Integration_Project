package com.example.AIWorldBuilder.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtils {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void deleteDirectoryRecursively(Path path) throws IOException {
        if (Files.notExists(path)) {
            return;
        }
        Files.walkFileTree(path, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static <T> T readJson(Path filePath, Class<T> type) {
        try {
            String json = Files.readString(filePath);
            return gson.fromJson(json, type);
        } catch (IOException e) {
            System.out.println("Error reading JSON from " + filePath + ": " + e.getMessage());
            return null;
        }
    }

    public static void writeJson(Path filePath, Object obj) {
        try {
            String json = gson.toJson(obj);
            Files.writeString(filePath, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error writing JSON to " + filePath + ": " + e.getMessage());
        }
    }

    public static void ensureDir(Path dirPath) {
        try {
            if (Files.notExists(dirPath)) {
                Files.createDirectories(dirPath);
            }
        } catch (IOException e) {
            System.out.println("Error ensuring directory " + dirPath + ": " + e.getMessage());
        }
    }
}
