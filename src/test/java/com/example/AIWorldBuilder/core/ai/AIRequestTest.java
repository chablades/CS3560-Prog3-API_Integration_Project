package com.example.AIWorldBuilder.core.ai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AIRequestTest {

    @Test
    void constructorStoresFields() {
        AIRequest req = new AIRequest(
                AIProvider.GOOGLE,
                AIRequestType.TEXT,
                "key",
                "Prompt",
                "Context",
                true
        );

        assertEquals(AIProvider.GOOGLE, req.getProvider());
        assertEquals(AIRequestType.TEXT, req.getRequestType());
        assertEquals("key", req.getApiKey());
        assertEquals("Prompt", req.getPrompt());
        assertEquals("Context", req.getContext());
        assertTrue(req.isStream());
    }
}