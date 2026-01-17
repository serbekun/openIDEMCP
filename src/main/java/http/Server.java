package http;

import io.javalin.Javalin;

// import handles
import http.Handles.*;
import servers.Ollama.Ollama.Generate;


public class Server {
    
    private Javalin svr;
    private int port;

    private Handles handles;
    private Logger logger;

    private boolean serverIsRunning;

    private Generate ollamaGenerator;

    public Server(Javalin svr,int port, String logFile, Generate ollamaGenerator) {
        this.port = port;

        this.svr = svr;

        this.handles = new Handles();
        this.logger = new Logger(logFile);

        this.ollamaGenerator = ollamaGenerator;

        InitHandles();
    }

    /**
     * Init server handles once
     */
    private void InitHandles() {
        svr.get("/v0/api/health", ctx -> handles.getHealth().Main(ctx, logger));
        svr.post("/v0/api/ask_model", ctx -> handles.getAskModel().Main(ctx, logger, ollamaGenerator));
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

    /**
     * Reload http server use StopHttpServer and RunHttpServer for reload server 
     */
    public void ReloadHttpServer() {
        StopHttpServer();
        StartHttpServer();
    }
}
