package com.example.AIWorldBuilder.core.ai.strategy;

public interface PromptStrategy {
    public String buildPrompt(String userPrompt, String context);
}
