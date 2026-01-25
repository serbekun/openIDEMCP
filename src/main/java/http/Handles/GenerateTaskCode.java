package http.Handles;


import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import servers.Generate.GenerateInterface;
import servers.dto.*;

import http.Logger;
import http.dto.GenerateTaskCodeRequest;
import http.dto.GenerateTaskCodeResponse;

public class GenerateTaskCode {
    
    private GenerateTaskCodeRequest GetRequest(Context ctx) {

        GenerateTaskCodeRequest req;
        try {
            req = ctx.bodyAsClass(GenerateTaskCodeRequest.class);
        } catch (Exception e) {
            ctx.json(new GenerateTaskCodeResponse(null, -1, "Invalid json format", 0, 0, false, null));
            return null;
        }
        return req;

    }

    public void Main(Context ctx, Logger logger, GenerateInterface generate) {

        int prompt_eval_count = 0;
        int eval_count = 0;

        logger.log(ctx.ip() + " request /v0/api/generate_task_code");
        // get JSON body
        var req = GetRequest(ctx);
        if (req.prompt == null || req.prompt.isBlank()) {
            ctx.json(new GenerateTaskCodeResponse(null, req.task_id, "Missing required fields",
             0, 0, false, null));
            return;
        }

        // try generate code
        try {
            
            // init generate response objects
            GenerateResponse generateResponseCode = null;
            GenerateResponse generateResponseExplanation = null;

            String prompt = req.prompt;

            String code_prompt = String.format(
                "Output only code and nothing else. " +
                "no markdown. " +
                "no explanation. " +
                "without include, import, use. " +
                "user prompt: %s",
                prompt
            );
            try {
                generateResponseCode = generate.execute(req.model, code_prompt, 0.7);
            } catch (Exception e) {
                logger.log("Error executing model '" + req.model + "': " + e.getMessage());
                ctx.json(new GenerateTaskCodeResponse(null, req.task_id,
                    "Model error: " + e.getMessage(), 0, 0, false, null));
                return;
            }
            
            if (generateResponseCode == null) {
                ctx.json(new GenerateTaskCodeResponse(null, req.task_id,
                    "Model did not return a response", 0, 0, false, null));
                return;
            }
            
            String code = generateResponseCode.response;
            if (code == null) {
                ctx.json(new GenerateTaskCodeResponse(null, req.task_id,
                    "Model returned empty response", 0, 0, false, null));
                return;
            }
           
            // explanation
            String explanation = null;
            if (req.explanation_mode) {
                String explanationPrompt = String.format(
                "Imagine you're the developer who wrote this code. Explain it as the author, " +
                "starting with the phrase, I wrote this code to..." +
                "Explain like you say to another developer what you write" +
                "Code: %s",
                code);
                
                try {
                    generateResponseExplanation = generate.execute(req.model,
                    explanationPrompt, 0.2);

                } catch (Exception e) {
                    System.out.println("Error Generate Explanation");
                    ctx.json(new GenerateTaskCodeResponse("null", req.task_id, "Error generate explanation",
                     0, 0, false, null ));
                    return;
                }

                explanation = generateResponseExplanation.response;
            }

            // calculate token usage
            
            // code
            prompt_eval_count += generateResponseCode.prompt_eval_count;
            eval_count += generateResponseCode.eval_count;

            // explanation
            prompt_eval_count += generateResponseExplanation.prompt_eval_count;
            eval_count += generateResponseExplanation.eval_count;

            // Send
            ctx.json(new GenerateTaskCodeResponse(code, req.task_id, null,
            prompt_eval_count, eval_count, true, explanation));

        } catch (IllegalArgumentException e) {

            logger.log("[http-server]Base64 decoding error: " + e.getMessage());
            ctx.json(new GenerateTaskCodeResponse(null, req.task_id, "Error decoding base64",
            0, 0, false, null));

        } catch (Exception e) {

            logger.log("Error in generate.execute(): " + e.getMessage());
            e.printStackTrace();
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json(new GenerateTaskCodeResponse(null, req.task_id, "Internal Server Error",
            0, 0, false, null));

        }
    }
}