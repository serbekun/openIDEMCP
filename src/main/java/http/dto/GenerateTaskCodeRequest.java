package http.dto;

public class GenerateTaskCodeRequest {
    public String prompt;
    public int task_id;
    public String model;
    public boolean explanation_mode;

    public GenerateTaskCodeRequest() {}

    public GenerateTaskCodeRequest(String prompt, int task_id, String model,
        boolean explanation_mode) 
    {
            this.prompt = prompt;
        this.task_id = task_id;
        this.model = model;
        this.explanation_mode = explanation_mode;
    }
}
