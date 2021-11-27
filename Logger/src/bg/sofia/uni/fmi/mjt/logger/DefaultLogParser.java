package bg.sofia.uni.fmi.mjt.logger;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class DefaultLogParser implements LogParser{
    public DefaultLogParser(Path logsFilePath) {}

    @Override
    public List<Log> getLogs(Level level) {
        return null;
    }

    @Override
    public List<Log> getLogs(LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override
    public List<Log> getLogsTail(int n) {
        return null;
    }
}
