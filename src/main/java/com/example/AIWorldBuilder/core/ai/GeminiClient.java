package com.example.AIWorldBuilder.core.ai;

import com.google.genai.Client;
import com.google.genai.ResponseStream;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

// Client for interacting with OpenAI API
public class GeminiClient {

    private boolean initialized = false;
    private static volatile GeminiClient instance = null;
    private Client client;

    // Get singleton instance
    public static GeminiClient getInstance() {
        if (instance == null) {
            synchronized (GeminiClient.class) {
                if (instance == null) {
                    instance = new GeminiClient();
                }
            }
        }
        return instance;
    }

    // Send a request to the Gemini API
    public void sendRequest(AIRequest request, AIStreamListener listener) {

        if (request.isTestMode()) {
            throw new IllegalArgumentException("Test mode requests should not reach GeminiClient.");
        }

        try {
            client = Client.builder()
                    .apiKey(request.getApiKey())
                    .build();
            initialized = true;
        }
        catch (Exception e) {
            listener.onError(e);
            return;
        }

        if (!initialized) {
            listener.onError(new IllegalStateException("GeminiClient not initialized."));
            return;
        }
        
        String prompt = request.getPrompt();

        try {
            var model = "gemini-2.5-flash";

            // Start streaming response
            ResponseStream<GenerateContentResponse> stream =
                client.models.generateContentStream(model, prompt, null);

            for (GenerateContentResponse res : stream) {
                String chunkText = res.text();
                if (chunkText != null && !chunkText.isEmpty()) {
                    listener.onToken(chunkText);
                }
            }

            // End of stream
            stream.close();
            listener.onToken("\n\n");
            listener.onComplete();
        }
        catch (Exception e) {
            String msg = e.getMessage();

            if (msg != null && (
                msg.contains("API key") ||
                msg.contains("401") ||
                msg.contains("403") ||
                msg.toLowerCase().contains("unauthorized") ||
                msg.toLowerCase().contains("invalid")
            )) {
                listener.onError(new InvalidApiKeyException("Invalid API key."));
            } else {
                listener.onError(e);
            }
            return;
        }
    }

}