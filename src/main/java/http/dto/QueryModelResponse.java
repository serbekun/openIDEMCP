package http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryModelResponse {
    
    public String response;
    public boolean success;

    @JsonProperty("error_message")
    public String errorMessage;

    public QueryModelResponse() {}

    public QueryModelResponse(String response, boolean success, String errorMessage) {
        this.response = response;
        this.success = success;
        this.errorMessage = errorMessage;
    }
}