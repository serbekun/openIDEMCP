package http.Handles;

import io.javalin.http.Context;

import http.Logger;

// import json dto
import http.dto.HealthResponse;

public class Health {
    
    public void Main(Context ctx, Logger logger) {

        
        logger.log(ctx.ip() + " request /v0/api/health");

        ctx.json(new HealthResponse(true));
    }

}
