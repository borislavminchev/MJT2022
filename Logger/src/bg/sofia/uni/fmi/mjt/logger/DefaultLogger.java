package bg.sofia.uni.fmi.mjt.logger;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class DefaultLogger implements Logger{

    public DefaultLogger(LoggerOptions options) {}

    @Override
    public void log(Level level, LocalDateTime timestamp, String message) {

    }

    @Override
    public LoggerOptions getOptions() {
        return null;
    }

    @Override
    public Path getCurrentFilePath() {
        return null;
    }
}
