package servers.Ollama;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.OllamaResult;

import java.io.IOException;

public class Ollama {
    private final OllamaAPI api;
    
    public Ollama(String baseUrl) {
        this.api = new OllamaAPI(baseUrl);
    }
    
    public String generate(String model, String prompt) {
        try {
            OllamaResult result = api.generate(model, prompt, false, null, null);
            
            return result.getResponse();
        } catch (IOException | InterruptedException | OllamaBaseException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}