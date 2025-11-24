package com.example.AIWorldBuilder.core.ai;

public interface AIStreamListener {
    void onToken(String token);
    void onComplete();
    void onError(Exception e);
    void onCancelled();
}