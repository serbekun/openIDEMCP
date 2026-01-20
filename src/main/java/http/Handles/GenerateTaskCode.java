package http.Handles;

import http.Logger;
import io.javalin.http.Context;

public class GenerateTaskCode {
    
    public void Main(Context ctx, Logger logger) {

        logger.log(ctx.ip() + " request /v0/api/generate_task_code");

        // TO DO continue make functionally of that handles


    }

}
