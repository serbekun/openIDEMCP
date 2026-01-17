package Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class Config {
    
    /**
     * Class for contain only config variables
     */
    private static class ConfigJson {
        public String server_folder;
        public String configFile;
        public int port;
        public String logFile;
        public String ollamaBaseUrl;
        
        public ConfigJson() {
            this.server_folder = "openIDEMCP/";
            this.configFile = "server_config.json";
            this.port = 8080;
            this.logFile = server_folder + "log.log";
            this.ollamaBaseUrl = "http://localhost:11434/";
        }
    }
    
    private final ObjectMapper mapper;
    private ConfigJson configJson;
    private final List<String> needFolders;
    
    public Config() {
        this.mapper = new ObjectMapper();
        this.configJson = new ConfigJson();
        this.needFolders = new ArrayList<>();
        
        // Initialize folders list
        needFolders.add(configJson.server_folder);
        
        // Load existing config if available
        loadFromDisk(false);
    }
    
    /**
     * Method for load config from disk
     */
    private synchronized void loadFromDisk(boolean isServerStart) {
        Path path = Paths.get(configJson.configFile);
        try {
            if (Files.exists(path) && Files.size(path) > 0) {
                String json = Files.readString(path);
                // Deserialize config from file
                ConfigJson loaded = mapper.readValue(json, new TypeReference<ConfigJson>() {});
                
                // Update current config with loaded values
                if (loaded.server_folder != null) {
                    configJson.server_folder = loaded.server_folder;
                }
                if (loaded.port > 0) {
                    configJson.port = loaded.port;
                }
                if (loaded.logFile != null) {
                    configJson.logFile = loaded.logFile;
                }
                if (loaded.ollamaBaseUrl != null) {
                    configJson.ollamaBaseUrl = loaded.ollamaBaseUrl;
                }
                
                // Update folder list if needed
                needFolders.clear();
                needFolders.add(configJson.server_folder);
                
                System.out.println("Configuration loaded from " + configJson.configFile);
            }
        } catch (IOException e) {
            if (!isServerStart) {
                System.err.println("Failed to load configuration from " + configJson.configFile + 
                                 ", using default values: " + e.getMessage());
            }
        }
    }
    
    /**
     * Method for update server config
     */
    public synchronized void updateConfig(String serverFolder, Integer port, String logFile, String ollamaBaseUrl) {
        if (serverFolder != null && !serverFolder.isEmpty()) {
            configJson.server_folder = serverFolder.endsWith("/") ? serverFolder : serverFolder + "/";
        }
        if (port != null && port > 0) {
            configJson.port = port;
        }
        if (logFile != null && !logFile.isEmpty()) {
            configJson.logFile = logFile;
        }
        if (ollamaBaseUrl != null && !ollamaBaseUrl.isEmpty()) {
            configJson.ollamaBaseUrl = ollamaBaseUrl.endsWith("/") ? ollamaBaseUrl : ollamaBaseUrl + "/";
        }
        
        // Update folder list
        needFolders.clear();
        needFolders.add(configJson.server_folder);
    }
    
    /**
     * Method for write config to Disk
     */
    public synchronized void writeJson() {
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(configJson);
            Files.writeString(Paths.get(configJson.configFile), json,
            StandardOpenOption.CREATE,
            StandardOpenOption.WRITE,
            StandardOpenOption.TRUNCATE_EXISTING);
            
            System.out.println("Configuration saved to " + configJson.configFile);
        } catch (IOException e) {
            System.err.println("[Config] Error writing configuration: " + e.getMessage());
        }
    }
    
    /**
     * Method to create necessary folders
     * @return true if folders was successfully created. false if folders was't successfully created
     */
    public synchronized boolean createNeededFolders() {
        try {
            for (String folderPath : needFolders) {
                Path folder = Paths.get(folderPath);
                if (!Files.exists(folder)) {
                    Files.createDirectories(folder);
                    System.out.println("Created folder: " + folderPath);
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("[Config] Error creating folders: " + e.getMessage());
            return false;
        }
    }
    
    // Optional: Method to get JSON representation
    public synchronized String toJsonString() throws IOException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(configJson);
    }
    
    // =======
    // Getters
    // =======
    
    public synchronized int getPort() {
        return configJson.port;
    }
    
    public synchronized String getLogFile() {
        return configJson.logFile;
    }
    
    public synchronized String getOllamaBaseUrl() {
        return configJson.ollamaBaseUrl;
    }
    
    public synchronized String getServerFolder() {
        return configJson.server_folder;
    }
    
    public synchronized String getConfigFilePath() {
        return configJson.configFile;
    }
    
    public synchronized List<String> getNeedFolders() {
        return new ArrayList<>(needFolders); // Return a copy to maintain encapsulation
    }
}