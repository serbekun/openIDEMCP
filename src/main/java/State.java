import io.javalin.Javalin;

import Config.Config;

public class State {

    private Javalin svr;
    private Config config;

    public State(Config config) {
        this.config = config;

        this.svr = Javalin.create();
    }

    public synchronized Javalin getSvr() {
        return svr;
    }
    
}
