package com.example.AIWorldBuilder.core.persistence;

import com.example.AIWorldBuilder.core.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilsTest {

    @TempDir
    Path tempDir;

    @Test
    void ensureDirCreatesDirectory() {
        Path dir = tempDir.resolve("testdir");
        FileUtils.ensureDir(dir);

        assertTrue(Files.exists(dir));
        assertTrue(Files.isDirectory(dir));
    }

    @Test
    void writeAndReadJsonWorks() {
        Path file = tempDir.resolve("data.json");

        TestObj obj = new TestObj("Hello");
        FileUtils.writeJson(file, obj);

        TestObj loaded = FileUtils.readJson(file, TestObj.class);

        assertNotNull(loaded);
        assertEquals("Hello", loaded.value);
    }

    private static class TestObj {
        String value;
        TestObj() {}
        TestObj(String value) { this.value = value; }
    }
}