package servers.Ollama.dto;

public class GenerateResponse {
    private String response;

    public GenerateResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
