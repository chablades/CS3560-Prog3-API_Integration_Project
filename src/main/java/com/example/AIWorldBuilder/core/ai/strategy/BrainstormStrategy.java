package com.example.AIWorldBuilder.core.ai.strategy;

public class BrainstormStrategy implements PromptStrategy {
    @Override
    public String buildPrompt(String userPrompt, String context) {
        // For BRAINSTORM mode, we ask the AI to generate ideas based on the user prompt
        return "You are brainstorming ideas with these instructions:\n\n" + userPrompt + "\n\nProvide creative ideas and suggestions. Heres the current context:\n" + context;
    }
    
}
