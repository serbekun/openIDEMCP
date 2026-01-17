package http.dto;

public class AskModelRequest {
    
    public String prompt;
    public String model;

    public AskModelRequest() {}

    public AskModelRequest(String prompt, String model) {
        this.prompt = prompt;
        this.model = model;
    } 
}