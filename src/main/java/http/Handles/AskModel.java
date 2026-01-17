package http.Handles;

import io.javalin.http.Context;
import http.Logger;

public class AskModel {
    
    /**
     * POST /v0/api/ask_model handles for get response about user question from model
     * 
     * <p><strong>Request example body:</strong></p>
     * <pre><code>{
     *      "prompt": "V2hhdCB0aGUgc2lnbmF0dXJlIG9mIGZnZXRzIGZ1bmN0aW9uPw==" // prompt base64 encoded text
     *      "model": "gemma3:4b" // model name
     * }</code></pre>
     * 
     * <p><string>Response example body:</string></p>
     * <pre><code>{
     *      "response": "Y2hhciAqZmdldHMoY2hhciAqc3RyLCBpbnQgbiwgRklMRSAqc3RyZWFtKTsK" // model base64 encoded response
     *      
     * }</code></pre>
     * 
     */
    public void Main(Context ctx, Logger logger) {

        logger.log(ctx.ip() + " request /v0/api/ask_model");

        // TODO make logic

    }

}
