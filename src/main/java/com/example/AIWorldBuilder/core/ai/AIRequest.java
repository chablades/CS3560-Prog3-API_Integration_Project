package com.example.AIWorldBuilder.core.ai;

public class AIRequest {
    private AIProvider provider;
    private AIRequestType requestType;

    private String apiKey;
    private String prompt;
    private String context;

    private boolean stream;
    private boolean testMode; // If True, no actual API call is made

    public AIRequest(AIProvider provider, AIRequestType requestType, String apiKey, String prompt, String context, boolean stream) {
        this.provider = provider;
        this.requestType = requestType;
        this.apiKey = apiKey;
        this.prompt = prompt;
        this.context = context;
        this.stream = stream;
        this.testMode = false;
    }

    // Getters and Setters
    public AIProvider getProvider() { return provider; }
    public AIRequestType getRequestType() { return requestType; }
    public String getApiKey() { return apiKey; }
    public String getPrompt() { return prompt; }
    public String getContext() { return context; }
    public boolean isStream() { return stream; }
    public boolean isTestMode() { return testMode; }
    public void setTestMode(boolean testMode) { this.testMode = testMode; }


}
