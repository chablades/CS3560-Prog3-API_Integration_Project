package com.example.AIWorldBuilder.testutil;

import com.example.AIWorldBuilder.core.ai.AIStreamListener;

import java.util.ArrayList;
import java.util.List;

public class TestListener implements AIStreamListener {

    private final List<String> tokens = new ArrayList<>();
    private boolean completed = false;
    private Exception error = null;
    private boolean cancelled = false;

    @Override
    public void onToken(String token) {
        tokens.add(token);
    }

    @Override
    public void onComplete() {
        completed = true;
    }

    @Override
    public void onError(Exception e) {
        error = e;
    }

    @Override
    public void onCancelled() {
        cancelled = true;
    }

    public List<String> getTokens() { return tokens; }
    public boolean isCompleted() { return completed; }
    public Exception getError() { return error; }
    public boolean isCancelled() { return cancelled; }
}