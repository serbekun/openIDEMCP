import io.javalin.Javalin;

class Main {
    public static void main(String[] args) {
        System.out.println("start MCP Server"); // example start message 
        Javalin svr = Javalin.create();
        
        svr.get("/api/ping", ctx -> ctx.result("pong"));
        svr.start(8080);
    }
}
 