package bg.sofia.uni.fmi.mjt.analyzer;

import bg.sofia.uni.fmi.mjt.analyzer.api.Response;
import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class ClientRequestHandler implements Runnable {
    private Socket socket;
    private RequestExecutor executor;

    public ClientRequestHandler(Socket socket, RequestExecutor executor) {
        this.socket = socket;
        this.executor = executor;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // autoflush on
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) { // read the message from the client
                System.out.println("Message received from client: " + inputLine);
                Response response = null;
                try {
                    response = executor.execute(inputLine);
                } catch (Exception e) {
                    out.println("Error occurred: " + e.getMessage());
                }

                if (response == null) {
                    out.println("No information retrieved from command: " + inputLine);
                } else {
                    List<Food> foods = response.getFoods();

                    String str = foods.stream().map(i -> i.toString()).collect(Collectors.joining("\n"));
                    out.println(str);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}