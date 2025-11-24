package com.example.AIWorldBuilder.core.ai;

import com.example.AIWorldBuilder.testutil.TestListener;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AIServiceErrorTest {

    @Test
    void invalidApiKeyIsReportedAsError() {
        TestListener listener = new TestListener();

        listener.onError(new InvalidApiKeyException("Invalid API key"));

        assertNotNull(listener.getError());
        assertTrue(listener.getError() instanceof InvalidApiKeyException);
        assertEquals("Invalid API key", listener.getError().getMessage());
    }

    @Test
    void networkFailureIsReportedAsError() {
        TestListener listener = new TestListener();

        listener.onError(new RuntimeException("Network error"));

        assertNotNull(listener.getError());
        assertEquals("Network error", listener.getError().getMessage());
    }
}