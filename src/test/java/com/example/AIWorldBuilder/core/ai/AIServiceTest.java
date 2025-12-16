package com.example.AIWorldBuilder.core.ai;

import com.example.AIWorldBuilder.core.service.AIService;
import com.example.AIWorldBuilder.testutil.TestListener;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AIServiceTest {

    @Test
    void sendInTestModeCompletesWithoutError() throws InterruptedException {
        AIService service = new AIService();

        AIRequest request = new AIRequest(
                AIProvider.GOOGLE,
                AIRequestType.TEXT,
                "dummy-key",
                "Test prompt",
                true
        );
        request.setTestMode(true);

        TestListener listener = new TestListener();

        service.send(request, listener);

        // ✅ Wait for the background thread to finish
        Thread.sleep(200); // or use a latch for cleaner sync

        assertFalse(listener.isCompleted());
        assertNull(listener.getError());
    }
}