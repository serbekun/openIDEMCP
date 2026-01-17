package State;
import io.javalin.Javalin;

import servers.Ollama.Ollama;
import Config.Config;

public class State {

    /**
     * http-server Javalin object
     */
    private Javalin svr; 
    private Ollama ollama;
    private Config config;

    public State(Config config) {
        this.svr = Javalin.create();
        this.ollama = new Ollama(config.getOllamaBaseUrl());
        this.config = config;
    }

    /**
     * @return Javalin server object
     */
    public synchronized Javalin getSvr() { return svr; }

    /**
     * @return Ollama object
     */
    public synchronized Ollama getOllama() { return ollama; }
    
}
