package bg.sofia.uni.fmi.mjt.logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultLogParser implements LogParser {
    private Path logsFilePath;
    public DefaultLogParser(Path logsFilePath) {
        this.logsFilePath = logsFilePath;
    }

    @Override
    public List<Log> getLogs(Level level) {
        List<Log> logs = new ArrayList<>();

        try (BufferedReader bufferedReader = Files.newBufferedReader(this.logsFilePath)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("[" + level.toString() + "]")) {
                    logs.add(parseLog(line));
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while reading from a file", e);
        }

        return logs;
    }

    @Override
    public List<Log> getLogs(LocalDateTime from, LocalDateTime to) {
        List<Log> logs = new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(this.logsFilePath)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Log currentLog = parseLog(line);
                if (currentLog.timestamp().isAfter(from) && currentLog.timestamp().isBefore(to)) {
                    logs.add(currentLog);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while reading from a file", e);
        }

        return logs;
    }

    @Override
    public List<Log> getLogsTail(int n) {
        List<Log> logs = new ArrayList<>();

        try {
            List<String> lines = new ArrayList<>(Files.readAllLines(this.logsFilePath));
            Collections.reverse(lines);
            for (int i = 0; i < n; i++) {
                String currentLine = lines.get(i);
                if (currentLine != null || !currentLine.isEmpty()) {
                    logs.add(parseLog(currentLine));
                }
            }

        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while reading from a file", e);
        }

        return logs;
    }

    private static Log parseLog(String log) {
        String[] elements = log.split("\\|");
        return new Log(parseLevel(elements[0]), LocalDateTime.parse(elements[1]), elements[2], elements[3]);
    }

    private static Level parseLevel(String level) {
        if (level == null || level.isEmpty()) {
            throw new IllegalArgumentException("Level cannot be empty or null");
        }
        return switch (level) {
            case "[DEBUG]" -> Level.DEBUG;
            case "[INFO]" -> Level.INFO;
            case "[WARN]" -> Level.WARN;
            case "[ERROR]" -> Level.ERROR;
            default -> throw new RuntimeException("No level with name: " + level);
        };
    }
}
