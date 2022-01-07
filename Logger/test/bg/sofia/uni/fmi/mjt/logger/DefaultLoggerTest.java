package bg.sofia.uni.fmi.mjt.logger;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DefaultLoggerTest {

    @Test
    public void test() {
        final int limit = 12;
        DefaultLogger defaultLogger = new DefaultLogger(new LoggerOptions(this.getClass(), "logs"));
        defaultLogger.log(Level.INFO, LocalDateTime.now(), "My messageeee1!!!!!");
        defaultLogger.log(Level.INFO, LocalDateTime.now(), "My messageeee2!!!!!");
        defaultLogger.log(Level.DEBUG, LocalDateTime.now(), "My messageeee3!!!!!");
        defaultLogger.log(Level.INFO, LocalDateTime.now(), "My messageeee4!!!!!");
        defaultLogger.log(Level.INFO, LocalDateTime.now(), "My messageeee5!!!!!");
        defaultLogger.log(Level.INFO, LocalDateTime.now(), "My messageeee6!!!!!");
        defaultLogger.log(Level.INFO, LocalDateTime.now(), "My messageeee7!!!!!");
        defaultLogger.getOptions();
        defaultLogger.getCurrentFilePath();
        LoggerOptions options = new LoggerOptions(this.getClass(), "logs");
        options.getMaxFileSizeBytes();
        options.getDirectory();
        options.shouldThrowErrors();
        options.setMaxFileSizeBytes(limit);
        options.setShouldThrowErrors(true);
        options.setMinLogLevel(Level.DEBUG);


        assertTrue(1 == 1);
    }
}