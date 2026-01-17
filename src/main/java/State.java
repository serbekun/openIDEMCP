import io.javalin.Javalin;

public class State {

    /**
     * http-server Javalin object
     */
    private Javalin svr; 

    public State() {
        this.svr = Javalin.create();
    }

    /**
     * @return Javalin server object
     */
    public synchronized Javalin getSvr() {
        return svr;
    }
    
}
