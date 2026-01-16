package Config;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private String server_folder;

    private int port;

    private String logFile;

    private List<String> needFolders;

    public Config() {

        this.needFolders = new ArrayList<String>();

        this.server_folder = "openIDEMCP/";        

        this.port = 8080;
        this.logFile = server_folder + "log.log";
    }

    public synchronized int getPort() { return port; }
    public synchronized String getLogFile() { return logFile; }

    public synchronized List<String> getNeedFolders() {

        needFolders.add(server_folder);
    
        return needFolders;
    }

}