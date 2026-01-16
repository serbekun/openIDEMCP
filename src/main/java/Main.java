import java.util.List;

import Config.Config;
import http.Server;

import java.io.File;

public class Main {

    
    /**
     * 
     * @param needFolders List of folders that need to create
    */
    private static void CreateNeedFolders(List<String> needFolders) {
    
        for (int i = 0; i < needFolders.size(); i++) {
            File folder = new File(needFolders.get(i));

            if (!folder.exists() && !folder.isDirectory()) {
                folder.mkdir();
            }
        }
    }


    public static void main(String[] args) {
        
        Config config = new Config(); 
        State state = new State(config);

        List<String> needFolders = config.getNeedFolders();
        
        CreateNeedFolders(needFolders);

        Server server = new Server(state.getSvr(),
                config.getPort(), config.getLogFile());

        server.StartHttpServer();
    }
}
