package Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        public String llmServer;

        public ConfigJson() {
            this.server_folder = "openIDEMCP/";
            this.configFile = Paths.get(System.getProperty("user.dir"), "openIDEMCP", "server_config.json")
                       .toString();
            this.port = 8080;
            this.logFile = server_folder + "log.log";
            this.ollamaBaseUrl = "http://localhost:11434/";
            this.llmServer = "ollama";
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
        loadFromDisk();
    }
    
    private synchronized void loadFromDisk() {
        Path path = Paths.get(configJson.configFile);
        try {
            if (!Files.exists(path) || Files.size(path) == 0) {
                System.out.println("[Config] Config file not found or empty → creating default");
                writeJson();
                return;
            }

        } catch (Exception e) {
            System.err.println("[Config] Error check file " + e.getMessage());
        }

        try {
            String json = Files.readString(path);
            ConfigJson loaded = mapper.readValue(json, ConfigJson.class);

            if (loaded.server_folder != null && !loaded.server_folder.isBlank()) {
                configJson.server_folder = loaded.server_folder;
            }
            if (loaded.port > 0) {
                configJson.port = loaded.port;
            }
            if (loaded.logFile != null && !loaded.logFile.isBlank()) {
                configJson.logFile = loaded.logFile;
            }
            if (loaded.ollamaBaseUrl != null && !loaded.ollamaBaseUrl.isBlank()) {
                configJson.ollamaBaseUrl = loaded.ollamaBaseUrl;
            }
            if (loaded.llmServer != null && !loaded.llmServer.isBlank()) {
                configJson.llmServer = loaded.llmServer;
            }

            needFolders.clear();
            needFolders.add(configJson.server_folder);

            System.out.println("[Config] Configuration loaded from: " + path.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("[Config] Invalid config file: " + e.getMessage());
            e.printStackTrace();
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
            System.out.println("[Config] Save config:\n" + json);
            Files.writeString(Paths.get(configJson.configFile), json,
            StandardOpenOption.CREATE,
            StandardOpenOption.WRITE,
            StandardOpenOption.TRUNCATE_EXISTING);
            
            System.out.println("[Config] Configuration saved to " + configJson.configFile);
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
                    System.out.println("[Config] Created folder: " + folderPath);
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

    public synchronized String getLlmServer() {
        return configJson.llmServer; 
    }
}