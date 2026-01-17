package http.Handles;

import io.javalin.http.Context;

import http.Logger;

// import json dto
import http.dto.HealthResponse;

public class Health {

    /**
     * GET /v0/api/health handles return server health
     * 
     * <p><strong>Response body:</strong></p>
     * <pre><code>{
     *     "status": true
     * }</code></pre>
     */
    public void Main(Context ctx, Logger logger) {

        logger.log(ctx.ip() + " request /v0/api/health");

        ctx.json(new HealthResponse(true));
    }

}
