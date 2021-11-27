package bg.sofia.uni.fmi.mjt.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class DefaultLogger implements Logger {
    private static final String DEFAULT_FILE_START = "log-";
    private static final String DEFAULT_FILE_END = ".txt";

    private final LoggerOptions options;
    private String activeFileName;


    public DefaultLogger(LoggerOptions options) {
        this.options = options;
        this.activeFileName = switch (getNumberOfFilesInDir()) {
            case 0 -> DEFAULT_FILE_START + "0" + DEFAULT_FILE_END;
            default -> DEFAULT_FILE_START + (getNumberOfFilesInDir() - 1) + DEFAULT_FILE_END;
        };
    }

    @Override
    public void log(Level level, LocalDateTime timestamp, String message) {
        if (level == null || timestamp == null || message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument was passed");
        }

        long maxSize = this.options.getMaxFileSizeBytes();

        if (Paths.get(this.options.getDirectory() + "/" + activeFileName).toFile().length() >= maxSize) {
            modifyToNextFile();
        }

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(this.options.getDirectory() + "/" + activeFileName, true))) {

            if (this.getOptions().getMinLogLevel().getLevel() <= level.getLevel()) {
                Log log = new Log(level, timestamp, this.options.getClazz().getPackage().toString(), message);
                writeToFile(writer, log);
            }

        } catch (IOException e) {
            if (this.options.shouldThrowErrors()) {
                throw new LogException(e.getMessage());
            }
        }
    }

    @Override
    public LoggerOptions getOptions() {
        return this.options;
    }

    @Override
    public Path getCurrentFilePath() {
        return Paths.get(options.getDirectory());
    }

    private int getNumberOfFilesInDir() {
        return this.getCurrentFilePath().toFile().listFiles().length;
    }

    private void modifyToNextFile() {
        this.activeFileName = DEFAULT_FILE_START + (getLastFileNumber() + 1) + DEFAULT_FILE_END;
    }

    private void writeToFile(BufferedWriter writer, Log log) throws IOException {
        writer.append(log.toString());
        writer.flush();
    }

    private int getLastFileNumber() {
        if (getNumberOfFilesInDir() == 0) {
            return -1;
        }

        return Integer.parseInt(this.activeFileName.substring( DEFAULT_FILE_START.length(),
                this.activeFileName.length() - DEFAULT_FILE_END.length()));
    }
}
