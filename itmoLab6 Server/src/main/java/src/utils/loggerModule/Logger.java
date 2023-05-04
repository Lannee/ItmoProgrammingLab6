package src.utils.loggerModule;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Logger {
    private BufferedWriter bufferedWriter; 

    public Logger(String aLoggerFileName) {
        try {
            bufferedWriter = Files.newBufferedWriter(Path.of(aLoggerFileName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeLogRecord(LogRecord logRecord) {
        try {
            bufferedWriter.write(logRecord.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
