package http.dto;

public class GenerateTaskCodeResponse {
    String code;
    int task_id;

    public GenerateTaskCodeResponse() {}

    public GenerateTaskCodeResponse(String code, int task_id) {
        this.code = code;
        this.task_id = task_id;
    }
}

