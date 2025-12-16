package com.example.AIWorldBuilder.core.ai.strategy;

public class RewriteStrategy implements PromptStrategy {
    @Override
    public String buildPrompt(String userPrompt, String context) {
        // For REWRITE mode, we instruct the AI to rewrite the existing context based on the user prompt
        return "You are rewriting the following story written here:\n\n" + context + "\n\nRewrite the story with the following instructions:\n" + userPrompt;
    }
    
}
