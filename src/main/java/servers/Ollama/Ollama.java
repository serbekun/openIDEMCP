package servers.Ollama;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import servers.Ollama.dto.GenerateResponse;

public class Ollama {
    private final String baseUrl;
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public Ollama(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    /**
     * 
     * Get ollama response
     * 
     * @param model The name of the model that will be used for the response
     * @param prompt prompt for model
     * @param temperature model temperature
     * @return response string
    */
   public GenerateResponse generate(String model, String prompt, Float temperature) {
       
       HttpClient client = HttpClient.newHttpClient();
       GenerateResponse generateResponse = null;
        
        String requestBody = String.format("""
                {
                    "model": "{model}",
                    "prompt": "{prompt}",
                    "stream": false
                    
                    "options": {
                        "temperature": {temperature}
                    }
                }
                """, model, prompt, temperature);

        System.out.printf("created request body %s\n", requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, BodyHandlers.ofString());


        } catch (IOException e) {
            System.err.println("Error make response: " + e);
        }
        catch (InterruptedException e) {
            System.err.println("Error make response: " + e);
        }

        if (response.statusCode() == 200) {

            String responseBody = response.body();
            try {
                var jsonNode = mapper.readTree(responseBody);
                jsonNode.get("");

                generateResponse = new GenerateResponse(jsonNode.get("response").asText());
            } catch (JsonProcessingException e) {
                System.err.println("Error parse response json: " + e);
            }

        }

        return generateResponse;
    }
    
}