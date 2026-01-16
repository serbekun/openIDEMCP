package http;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {

    String logFile;

    public Logger(String logFile) {

        this.logFile = logFile;
    }

    public void log(String log) {

        Path logPath = Paths.get(logFile);

        LocalDateTime currentDateTime = LocalDateTime.now();

        String final_log = currentDateTime + " " + log;

        try {
            Files.writeString(logPath, final_log, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error append to log file. Error message: " + e.getMessage());
        }
    }
}
