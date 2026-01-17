package servers.Ollama;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import servers.Ollama.dto.GenerateResponse;

public class Ollama {
    private final String baseUrl;
    private static final ObjectMapper mapper = new ObjectMapper();
    
    private final boolean DEBUG = true;

    public Ollama(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    @FunctionalInterface
    public interface Generate {
        GenerateResponse execute(String model, String prompt, Double temperature);
    }

    /**
     * Get ollama response
     * 
     * @param model The name of the model that will be used for the response
     * @param prompt prompt for model
     * @param temperature model temperature
     * @return response string
     */
    public GenerateResponse Generate(String model, String prompt, Double temperature) {
        HttpClient client = HttpClient.newHttpClient();
        GenerateResponse generateResponse = null;
        
        String requestBody = String.format("""
                {
                    "model": "%s",
                    "prompt": "%s",
                    "stream": false,
                    "options": {
                        "temperature": %s
                    }
                }
                """, 
                model.replace("\"", "\\\""),  // Escape quotes in model name
                prompt.replace("\"", "\\\""),  // Escape quotes in prompt
                temperature);
        
        System.out.printf("[Ollama] DEBUG: Created request body %s\n", requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (DEBUG) {
                System.out.println("[Ollama] DEBUG Response status code: " + response.statusCode());
                System.out.println("[Ollama] DEBUG Response body: " + response.body());
            }

            if (response.statusCode() == 200) {
                try {
                    var jsonNode = mapper.readTree(response.body());
                    
                    // Check if response field exists
                    if (jsonNode.has("response")) {
                        generateResponse = new GenerateResponse(jsonNode.get("response").asText());
                    } else {
                        System.err.println("[Ollama] No 'response' field in JSON: " + response.body());
                    }
                } catch (JsonProcessingException e) {
                    System.err.println("[Ollama] Error parse response json: " + e);
                    e.printStackTrace();
                }
            } else {
                System.err.println("[Ollama] HTTP Error: " + response.statusCode() + " - " + response.body());
            }
            
        } catch (IOException | InterruptedException e) {
            System.err.println("[Ollama] Error making request: " + e);
            e.printStackTrace();
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt(); // Restore interrupt status
            }
        }

        return generateResponse;
    }
}