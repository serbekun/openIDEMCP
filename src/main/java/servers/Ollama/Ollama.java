package servers.Ollama;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.OllamaResult;
import io.github.ollama4j.utils.Options;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Ollama {
    private final OllamaAPI api;
    
    public Ollama(String baseUrl) {
        this.api = new OllamaAPI(baseUrl);
    }
    
    /**
     * 
     * @param model The name of the model that will be used for the response
     * @param prompt prompt for model
     * @param temperature model temperature
     * @return response string
     */
    public String generate(String model, String prompt, Float temperature) {
        try {
            Options options = null;
            
            if (temperature != null) {
                Map<String, Object> optionsMap = new HashMap<>();
                optionsMap.put("temperature", temperature);
                
                options = new Options(optionsMap);
            }
            
            OllamaResult result = api.generate(model, prompt, false, options, null);
            
            return result.getResponse();
        } catch (IOException | InterruptedException | OllamaBaseException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}