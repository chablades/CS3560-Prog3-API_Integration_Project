package com.example.AIWorldBuilder.core.ai.strategy;

public class ContinueStrategy implements PromptStrategy {
    @Override
    public String buildPrompt(String userPrompt, String context) {
        // For CONTINUE mode, we simply append the user prompt to the existing context
        return "You are continuing the following story written here:\n\n" + context + "\n\nContinue the story with the following prompt:\n" + userPrompt;
    }
}