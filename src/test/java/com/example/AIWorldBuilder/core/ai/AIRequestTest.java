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
                true
        );

        assertEquals(AIProvider.GOOGLE, req.getProvider());
        assertEquals(AIRequestType.TEXT, req.getRequestType());
        assertEquals("key", req.getApiKey());
        assertEquals("Prompt", req.getPrompt());
        assertTrue(req.isStream());
    }
}