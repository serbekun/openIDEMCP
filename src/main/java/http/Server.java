package http;

import io.javalin.Javalin;

// import handles
import http.Handles.*;
import servers.Generate.GenerateInterface;

public class Server {
    
    private Javalin svr;
    private int port;

    private Handles handles;
    private Logger logger;

    private boolean serverIsRunning;

    private GenerateInterface generator;

    public Server(Javalin svr, int port, String logFile, GenerateInterface generator) {
        this.port = port;

        this.svr = svr;

        this.handles = new Handles();
        this.logger = new Logger(logFile);

        this.generator = generator;

        InitHandles();
    }

    /**
     * Init server handles once
     */
    private void InitHandles() {
        svr.get("/v0/api/health", ctx -> handles.getHealth().Main(ctx, logger));
        svr.post("/v0/api/query_model", ctx -> handles.getQueryModel().Main(ctx, logger, generator));
        svr.post("/v0/api/generate_task_code", ctx -> handles.getGenerateTaskCode().Main(ctx, logger, generator));
    }

    /**
     * Start http server
     *
     * @return boolean if return true server is started if false server is not was stopped 
     */
    public boolean StartHttpServer() {
        if (!serverIsRunning) {
            svr.start(port);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Stop http server
     * 
     * @return boolean if return true server is stopped if false server is not was running
    */
    public boolean StopHttpServer() {
       
        if (serverIsRunning) {
            svr.stop();
            serverIsRunning = false;
            return true;
        }
        else {
            return false;
        }
    }

    public interface StartStopServer {
        void run();
        void stop();
    }

    public void StartHttpServerVoid() {
        svr.start(port);
    }

    public void StopHttpServerVoid() {
        svr.stop();
    }

    /**
     * Reload http server use StopHttpServer and RunHttpServer for reload server 
     */
    public void ReloadHttpServer() {
        StopHttpServer();
        StartHttpServer();
    }
}
