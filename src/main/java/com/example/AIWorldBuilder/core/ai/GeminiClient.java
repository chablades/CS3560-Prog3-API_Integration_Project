package com.example.AIWorldBuilder.core.ai;

import com.google.genai.Client;
import com.google.genai.ResponseStream;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

// Client for interacting with OpenAI API
public class GeminiClient {

    private boolean initialized = false;
    private Client client;

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
        
        String finalPrompt = buildPrompt(request);

        try {
            var model = "gemini-2.5-flash";

            // Start streaming response
            ResponseStream<GenerateContentResponse> stream =
                client.models.generateContentStream(model, finalPrompt, null);

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

    // Build the full prompt with context
    private String buildPrompt(AIRequest request) {
        StringBuilder promptBulder = new StringBuilder();
        
        // System message
        promptBulder.append("SYSTEM: You are an AI assistant that helps users write creative stories. You will continue the story based on the user prompt and the last text of the story provided in the context. Be imaginative, listen to the user's input and style needs, and be engaging in your writing.");

        // User prompt
        promptBulder.append("\nUSER: " + request.getPrompt());

        // Context message (if any)
        promptBulder.append("\nPREVIOUS TEXT: " + request.getContext());
        
        // Final assistant prompt
        promptBulder.append("\nCONTINUE THE STORY: ");

        return promptBulder.toString();
    }


}