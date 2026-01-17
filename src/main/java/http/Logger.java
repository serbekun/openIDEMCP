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

    /**
     * Add time information to log and write to file
     * 
     * @param log log string that will be write to log file
     */
    public void log(String log) {

        // path to log file
        Path logPath = Paths.get(logFile);

        // current time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // adding current time to log
        String final_log = currentDateTime + " " + log;

        // trying write log to file
        try {
            // writing to file
            Files.writeString(logPath, final_log, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) { // cath error
            // show error log
            System.err.println("Error append to log file. Error message: " + e.getMessage());
        }
    }
}
