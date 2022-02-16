package bg.sofia.uni.fmi.mjt.analyzer;

import bg.sofia.uni.fmi.mjt.analyzer.api.DefaultFoodInfoReceiver;
import bg.sofia.uni.fmi.mjt.analyzer.storage.DefaultFoodStorage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {
    private static final int SERVER_PORT = 7777;
    private static final int MAX_EXECUTOR_THREADS = 10;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_EXECUTOR_THREADS);
        RequestExecutor requestExecutor = new RequestExecutor(new DefaultFoodInfoReceiver(new DefaultFoodStorage()));

        Logger logger = Logger.getLogger("Log");
        FileHandler handler = null;
        String name = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        try {
            handler = new FileHandler("logs/" + name + ".log");
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler );

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Server started and listening for connect requests");
            Socket clientSocket;

            while (true) {
                clientSocket = serverSocket.accept();
                logger.info("Accepted connection request from client " + clientSocket.getLocalAddress());

                ClientRequestHandler clientHandler = new ClientRequestHandler(clientSocket, requestExecutor, logger);
                executor.execute(clientHandler);
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the server socket", e);
        }
    }
}
