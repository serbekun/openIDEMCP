package http.dto;

public class GenerateTaskCodeRequest {
    String prompt;
    int task_id;

    public GenerateTaskCodeRequest() {}

    public GenerateTaskCodeRequest(String prompt, int task_id) {
        this.prompt = prompt;
        this.task_id = task_id;
    }
}
