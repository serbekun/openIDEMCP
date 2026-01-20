package http.Handles;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import http.Logger;

import http.dto.AskModelRequest;
import http.dto.AskModelResponse;

import servers.Ollama.Ollama.Generate;
import servers.Ollama.dto.GenerateResponse;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class QueryModel {
    
    /**
     * POST /v0/api/ask_model handles for get response about user question from model
     * 
     * <p><strong>Request example body:</strong></p>
     * <pre><code>{
     *      "prompt": "V2hhdCB0aGUgc2lnbmF0dXJlIG9mIGZnZXRzIGZ1bmN0aW9uPw==" // prompt base64 encoded text
     *      "model": "gemma3:4b" // model name
     * }</code></pre>
     * 
     * <p><strong>Success Response example body:</strong></p>
     * <pre><code>{
     *      "response": "Y2hhciAqZmdldHMoY2hhciAqc3RyLCBpbnQgbiwgRklMRSAqc3RyZWFtKTsK", // model base64 encoded response
     *      "success": true,
     *      "error_messages": null
     * }</code></pre>
     * 
     * Error responses
     * 
     * <p><strong>Invalid json format Response body:</strong></p>
     * <pre><code>{
     *      "response": null,
     *      "success": false,
     *      "error_message": "Invalid json format"
     * }</code></pre>
     * 
     * <p><strong>Missing required fields</strong></p>
     * <pre><code>{
     *      "response": null,
     *      "success": false,
     *      "error_message": "Missing required fields"
     * }
     * 
     * <p><strong>Invalid base64 encoding</strong></p>
     * <pre><code>{
     *      "response": null,
     *      "success": false,
     *      "error_message": "Invalid base64 encoding"     
     * }
     * 
     * <p><strong>Internal server error</strong></p>
     * <pre><code>{
     *      "response": null,
     *      "success": false,
     *      "error_message": "Internal server error"
     * }
     */
    public void Main(Context ctx, Logger logger, Generate generate) {
        logger.log(ctx.ip() + " request /v0/api/ask_model");

        // get JSON body
        AskModelRequest req;
        try {
            req = ctx.bodyAsClass(AskModelRequest.class);
        } catch (Exception e) {
            ctx.json(new AskModelResponse(null, false, "Invalid json format"));
            return;
        }

        // validation json format
        if (req.prompt == null || req.prompt.isBlank()
        || req.model == null || req.model.isBlank()) {
            ctx.json(new AskModelResponse(null, false, "Missing required fields"));
            return;
        }

        // try generate answer
        try {
            // decoding base64 to text
            byte[] decodedBytes = Base64.getDecoder().decode(req.prompt);
            String prompt = new String(decodedBytes, StandardCharsets.UTF_8);
            
            logger.log("Model: " + req.model + ", Prompt decoded length: " + prompt.length());
            
            // Try-catch around the generate.execute() call
            GenerateResponse generateResponse = generate.execute(req.model, prompt, 0.7);
            
            String response = generateResponse.getResponse();
            if (response == null) {
                throw new Exception("Generate response is null");
            }
            
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            
            String base64Response = Base64.getEncoder().encodeToString(bytes);

            ctx.json(new AskModelResponse(base64Response, true, null));
          
        } catch (IllegalArgumentException e) {
            logger.log("Base64 decoding error: " + e.getMessage());
            ctx.json(new AskModelResponse(null, false, "Invalid base64 encoding"));
        } catch (Exception e) {
            logger.log("Error in generate.execute(): " + e.getMessage());
            e.printStackTrace(); // Add this for server logs
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json(new AskModelResponse(null, false, "Internal server error"));
        }
    }
}