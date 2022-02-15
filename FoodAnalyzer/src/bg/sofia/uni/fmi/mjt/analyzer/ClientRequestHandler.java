package bg.sofia.uni.fmi.mjt.analyzer;

import bg.sofia.uni.fmi.mjt.analyzer.api.Response;
import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClientRequestHandler implements Runnable {
    private Socket socket;
    private RequestExecutor executor;
    private Logger logger;

    public ClientRequestHandler(Socket socket, RequestExecutor executor, Logger logger) {
        this.socket = socket;
        this.executor = executor;
        this.logger = logger;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // autoflush on
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) { // read the message from the client
                logger.info("Message received from " + socket.getInetAddress() + " : <" + inputLine + ">");
                Response response = null;
                try {
                    response = executor.execute(inputLine);
                    List<Food> foods = response.getFoods();
                    if (foods.isEmpty()) {
                        logger.info("No foods retrieved from command <" + inputLine + "> send FROM " +
                                socket.getInetAddress());
                        out.println("No foods retrieved from command: " + inputLine);
                    } else {
                        logger.info("Received response from command <" + inputLine + "> send FROM " +
                                socket.getInetAddress());
                        String str = foods.stream().map(i -> i.toString()).collect(Collectors.joining("\n"));
                        out.println(str);
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Exception occurred when executing command <" + inputLine
                            + "> FROM " + socket.getInetAddress(), e);
                    out.println("Error occurred: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception occurred FROM " + socket.getInetAddress(), e);
        } finally {
            try {
                logger.info(socket.getInetAddress() + " disconnected");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}