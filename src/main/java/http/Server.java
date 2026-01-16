package http;

import io.javalin.Javalin;

// import handles
import http.Handles.*;

public class Server {
    
    private Javalin svr;
    private int port;

    private Handles handles;
    private Logger logger;

    private boolean serverIsRunning;

    public Server(Javalin svr,int port, String logFile) {
        this.port = port;

        this.svr = svr;

        this.handles = new Handles();
        this.logger = new Logger(logFile);

        InitHandles();
    }

    public void InitHandles() {
        svr.get("/v0/api/health", ctx -> handles.getHealth().Main(ctx, logger));
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
