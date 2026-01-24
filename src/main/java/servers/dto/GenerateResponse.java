package servers.dto;

public class GenerateResponse {
    public String response;
    public int prompt_eval_count;
    public int eval_count;

    public GenerateResponse(String response, int prompt_eval_count, int eval_count) {
        this.response = response;
        this.prompt_eval_count = prompt_eval_count;
        this.eval_count = eval_count;
    }

}
