package com.example.AIWorldBuilder.core.ai.factory;

import com.example.AIWorldBuilder.core.ai.PromptMode;
import com.example.AIWorldBuilder.core.ai.strategy.*;

public class PromptStrategyFactory {
    public static PromptStrategy create(PromptMode mode) {
        return switch (mode) {
            case CONTINUE -> new ContinueStrategy();
            case REWRITE -> new RewriteStrategy();
            case BRAINSTORM -> new BrainstormStrategy();
            default -> new ContinueStrategy();
        };
    }
}
