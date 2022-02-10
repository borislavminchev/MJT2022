package bg.sofia.uni.fmi.mjt.analyzer;

import bg.sofia.uni.fmi.mjt.analyzer.api.FoodInfoReceiver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int SERVER_PORT = 7777;
    private static final int MAX_EXECUTOR_THREADS = 10;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_EXECUTOR_THREADS);

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);) {

            System.out.println("Server started and listening for connect requests");
            RequestExecutor requestExecutor = new RequestExecutor(new FoodInfoReceiver());
            Socket clientSocket;

            while (true) {

                // Calling accept() blocks and waits for connection request by a client
                // When a request comes, accept() returns a socket to communicate with this
                // client
                clientSocket = serverSocket.accept();

                System.out.println("Accepted connection request from client " + clientSocket.getInetAddress());

                // We want each client to be processed in a separate thread
                // to keep the current thread free to accept() requests from new clients

                ClientRequestHandler clientHandler = new ClientRequestHandler(clientSocket,requestExecutor);

                // uncomment the line below to launch a thread manually
                // new Thread(clientHandler).start();

                executor.execute(clientHandler); // use a thread pool to launch a thread
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the server socket", e);
        }
    }
}
