package bg.sofia.uni.fmi.mjt.logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultLogParser implements LogParser {
    private final Path logsFilePath;

    public DefaultLogParser(Path logsFilePath) {
        this.logsFilePath = logsFilePath;
    }

    private static Log parseLog(String log) {
        String[] elements = log.split("\\|");
        final int s = 3;
        return new Log(parseLevel(elements[0]), LocalDateTime.parse(elements[1]), elements[2], elements[s]);
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

    @Override
    public List<Log> getLogs(Level level) {
        if (level == null || level.getLevel() < 0) {
            throw new IllegalArgumentException();
        }
        List<Log> logs = new ArrayList<>();

        File file = this.logsFilePath.toFile();
        for (File f : file.listFiles()) {
            try (BufferedReader bufferedReader = Files.newBufferedReader(f.toPath())) {
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("[" + level.toString() + "]")) {
                        logs.add(parseLog(line));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }


        return logs;
    }

    @Override
    public List<Log> getLogs(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException();
        }

        List<Log> logs = new ArrayList<>();
        File file = this.logsFilePath.toFile();
        for (File f : file.listFiles()) {
            try (BufferedReader bufferedReader = Files.newBufferedReader(f.toPath())) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    Log currentLog = parseLog(line);
                    if (currentLog.timestamp().isAfter(from) && currentLog.timestamp().isBefore(to)) {
                        logs.add(currentLog);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }


        return logs;
    }

    private List<File> getAllFiles(File folder, List<File> res) {
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                res.add(file);
            } else {
                getAllFiles(file, res);
            }
        }

        return res;
    }

    @Override
    public List<Log> getLogsTail(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        List<Log> logs = new ArrayList<>();
        if (n == 0) {
            return logs;
        }

        File file = this.logsFilePath.toFile();
        int temp = n;
        List<String> allLines = new ArrayList<>();
        for (File f : file.listFiles()) {
            try {
                List<String> lines = new ArrayList<>(Files.readAllLines(f.toPath()));
                allLines.addAll(lines);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        Collections.reverse(allLines);
        for (int i = 0; i < n; i++) {
            String currentLine = allLines.get(i);
            if (currentLine != null || !currentLine.isEmpty()) {
                logs.add(parseLog(currentLine));
            }
        }

        return logs;
    }
}
