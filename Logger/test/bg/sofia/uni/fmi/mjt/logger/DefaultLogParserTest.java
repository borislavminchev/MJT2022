package bg.sofia.uni.fmi.mjt.logger;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DefaultLogParserTest {

    @Test
    public void test() {
        DefaultLogParser parser = new DefaultLogParser(Path.of("logs"));
        parser.getLogs(Level.INFO);
        parser.getLogs(LocalDateTime.now().minusDays(1), LocalDateTime.now());
        parser.getLogsTail(2);

        assertTrue(1 == 1);
    }
}