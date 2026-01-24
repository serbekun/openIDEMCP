package http.Handles;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
            // decoding base64 to text
            byte[] decodedBytes = Base64.getDecoder().decode(req.prompt);
            String prompt = new String(decodedBytes, StandardCharsets.UTF_8);

            String code_prompt = String.format(
                "Output only code and nothing else. " +
                "no markdown. " +
                "no explanation. " +
                "without include, import, use. " +
                "user prompt: %s",
                prompt
            );
           
            GenerateResponse generateResponseCode;
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
                
                GenerateResponse generateResponseExplanation = null;
                
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

            // encoding to base64 code
            byte[] bytes = code.getBytes(StandardCharsets.UTF_8);
            String base64Code = Base64.getEncoder().encodeToString(bytes);

            // encoding to base64 explanation
            bytes = explanation.getBytes(StandardCharsets.UTF_8);
            String base64Explanation = Base64.getEncoder().encodeToString(bytes);

            // Send
            ctx.json(new GenerateTaskCodeResponse(base64Code, req.task_id, null,
            0, 0, true, base64Explanation));

        } catch (IllegalArgumentException e) {

            logger.log("Base64 decoding error: " + e.getMessage());
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