package State;
import io.javalin.Javalin;

import Config.Config;
import servers.Generate;

public class State {

    /**
     * http-server Javalin object
     */
    private Javalin svr; 
    private Config config;
    private Generate generate;

    public State(Config config) {
        this.svr = Javalin.create();
        this.config = config;
        this.generate = new Generate(config.getLlmServer());
    }

    /**
     * @return Javalin server object
     */
    public synchronized Javalin getSvr() { return svr; }

    /**
     * @return generate object
     */
    public synchronized Generate getGenerate() { return generate; }
    
}
