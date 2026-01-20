package http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AskModelResponse {
    
    public String response;
    public boolean success;

    @JsonProperty("error_message")
    public String errorMessage;

    public AskModelResponse() {}

    public AskModelResponse(String response, boolean success, String errorMessage) {
        this.response = response;
        this.success = success;
        this.errorMessage = errorMessage;
    }
}