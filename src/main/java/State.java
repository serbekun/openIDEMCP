import io.javalin.Javalin;


public class State {

    private Javalin svr;

    public State() {
        this.svr = Javalin.create();
    }

    public synchronized Javalin getSvr() {
        return svr;
    }
    
}
