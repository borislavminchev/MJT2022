package bg.sofia.uni.fmi.mjt.logger;

import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LoggerOptions o = new LoggerOptions(A.class, "logs");
        o.setMaxFileSizeBytes(100);
        Logger logger = new DefaultLogger(o);
        logger.log(Level.INFO, LocalDateTime.now(), "My messageeee1!!!!!");
        logger.log(Level.INFO, LocalDateTime.now(), "My messageeee2!!!!!");
        logger.log(Level.DEBUG, LocalDateTime.now(), "My messageeee3!!!!!");
        logger.log(Level.INFO, LocalDateTime.now(), "My messageeee4!!!!!");
        logger.log(Level.INFO, LocalDateTime.now(), "My messageeee5!!!!!");
        logger.log(Level.INFO, LocalDateTime.now(), "My messageeee6!!!!!");
        logger.log(Level.INFO, LocalDateTime.now(), "My messageeee7!!!!!");
//        System.out.println(Level.INFO);
    }
}

class A {
//    Package cl =
    Package getP() {
        return this.getClass().getPackage();
    }
}
