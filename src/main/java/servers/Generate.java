package servers;

import servers.Ollama.Ollama;
import servers.dto.*;

public class Generate {
 
    private String llmServer;

    // servers
    private Ollama ollama;

    public Generate(String llmServer, String ollamaUrl) {
        this.llmServer = llmServer;

        this.ollama = new Ollama(ollamaUrl);
    }

    @FunctionalInterface
    public interface GenerateInterface {
        GenerateResponse execute(String model, String prompt, Double temperature);

        default GenerateResponse generate(String m, String p, Double t) {
            return execute(m, p, t);
        }
    }

    public GenerateResponse generateResponse(String model, String prompt, Double temperature) {

        GenerateResponse generateResponse = null;
        switch (llmServer) {
            case "ollama":
                generateResponse = ollamaGenerate(model, prompt, temperature);
                break;
        
            default:
                return null;
        }
        
        return generateResponse;

    }

    private GenerateResponse ollamaGenerate(String model, String prompt, Double temperature) {
        var temp = ollama.Generate(model, prompt, temperature);

        GenerateResponse result = new GenerateResponse(temp.response,
            temp.prompt_eval_count, temp.eval_count);

        return result;
    }
}
