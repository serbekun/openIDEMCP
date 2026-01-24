package http.dto;

public class GenerateTaskCodeResponse {
    String code;
    int task_id;
    String explanation;
        
    int prompt_eval_count;
    int eval_count;

    String message;
    boolean success;

    public GenerateTaskCodeResponse() {}

    public GenerateTaskCodeResponse(String code, int task_id,
        String message, int prompt_eval_count, int eval_count, boolean success, String explanation) {
        this.code = code;
        this.task_id = task_id;
        this.explanation = explanation;

        this.prompt_eval_count = prompt_eval_count;
        this.eval_count = eval_count;

        this.message = message;
        this.success = success;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public int getTask_id() {
        return task_id;
    }

    public String getExplanation() {
        return explanation;
    }

    public int getPrompt_eval_count() {
        return prompt_eval_count;
    }

    public int getEval_count() {
        return eval_count;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    // Setters
    public void setCode(String code) {
        this.code = code;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setPrompt_eval_count(int prompt_eval_count) {
        this.prompt_eval_count = prompt_eval_count;
    }

    public void setEval_count(int eval_count) {
        this.eval_count = eval_count;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

