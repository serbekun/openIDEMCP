package http.dto;

public class QueryModelRequest {
    
    public String prompt;
    public String model;

    public QueryModelRequest() {}

    public QueryModelRequest(String prompt, String model) {
        this.prompt = prompt;
        this.model = model;
    } 
}